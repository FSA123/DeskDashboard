# Desk Dashboard

A always-on desk dashboard for Geniatech color e-paper hardware. It runs as a headless Android foreground service that renders the time, date, weather, air quality, and your next calendar event to a 2560×1440 color EPD panel, refreshing continuously while managing the panel's slow refresh and ghosting behaviour.

The app draws everything natively to a `Bitmap` and pushes it to the panel through Geniatech's `EpdManager` AIDL service — there is no WebView or browser involved.

## Features

- **Clock & date** in Europe/Bucharest time, synced via NTP through the panel's system service. 24h or 12h format.
- **Weather** from the free [Open-Meteo](https://open-meteo.com/) API: current temperature, "feels like", daily high/low, condition, wind, and humidity. Polled hourly with retry + on-disk cache fallback for offline resilience.
- **Air quality** (European AQI) from the Open-Meteo air-quality API.
- **Next event** pulled from a Google Calendar iCal feed, showing the current (`NOW`) or upcoming (`NEXT`) event with a live countdown.
- **Daylight remaining** footer derived from sunrise/sunset.
- **Smart refresh policy** tuned for e-paper — partial-zone updates for small changes, full refreshes for anti-ghosting, plus a daily deep-clean cycle (details below).
- **Resilient by default** — auto-starts on boot, rebinds to the EPD service if it drops, and falls back to cached data when the network is down.

## Hardware

Built and tested against a Geniatech EPC-series device (RK3566) driving a 13.3" color e-paper panel via the `com.geniatech.el133sdk` `EpdManager` service. The display resolution is configurable; the default is **2560×1440**.

The app binds directly to the vendor EPD service:
1. Primary: `com.geniatech.epc.core` → `com.geniatech.el133sdk.epdService`
2. Fallback: `com.geniatech.epc.service` → `...SendImageService`

The bundled AIDL (`app/src/main/aidl/com/geniatech/el133sdk/EpdManager.aidl`) is treated as the authoritative interface definition.

## Architecture

Everything runs inside a single foreground `Service`; there is no UI beyond a launcher activity that starts it.

```
DashboardService            Orchestrator. Owns the tick loop, refresh policy, and EPD I/O.
├── EpdConnector            Binds the vendor AIDL service; send/partial-send bitmaps, NTP, clScr.
├── DashboardRenderer       Draws the full-screen Bitmap (Layout + Icons).
│   ├── Layout              Pixel rects for every zone (clock, date, weather, event, footer).
│   └── Icons               Vector-drawn weather/UI glyphs.
├── WeatherClient/Parser    Open-Meteo forecast + AQI → WeatherSnapshot (+ WeatherCache).
├── CalendarClient/ICalParser  Fetches & parses the iCal feed → CalendarEvent list (+ CalendarCache).
├── TimeTicker              Fires once per minute on the main thread.
├── WifiConnector           Brings up WiFi via the EPD service if needed.
└── BootReceiver            Restarts the service on BOOT_COMPLETED.
```

Data flows on a single worker thread: network callbacks and the per-minute tick all funnel into `tick()`, which diffs the new state against the last-rendered state and decides whether (and how) to repaint.

### Refresh policy

E-paper is slow and ghosts, so blind full repaints look bad and partial repaints accumulate artifacts. The service diffs each zone and:

- Sends **partial-zone** updates for small, disjoint changes (e.g. the clock minute) — each changed rect is sent as its own `sendpartImageBitmap` call, byte-aligned to 8 pixels, rather than one big bounding box.
- Forces a **full refresh** when any of these trip: the day rolls over, the weather code changes, fresh weather/calendar data arrives, a configurable consecutive-partial cap is hit, or a wall-clock anti-ghosting interval elapses.
- Runs a daily **deep clean** (`clScr` + full refresh) at a configured hour to reset the panel.

All thresholds are configurable (see below).

## Configuration

All tunables live in `gradle.properties` and are injected as `BuildConfig` fields at build time.

| Key | Default | Purpose |
| --- | --- | --- |
| `display_width` / `display_height` | `2560` / `1440` | Panel resolution |
| `weather.lat` / `weather.lon` | Bucharest | Location for weather + AQI |
| `weather.timezone` | `Europe/Bucharest` | Open-Meteo timezone |
| `weather.poll.minutes` | `60` | Weather poll interval |
| `clock.format` | `24` | `24` or `12` hour clock |
| `ntp.server` | `pool.ntp.org` | Time sync source |
| `calendar.ical.url` | — | Google Calendar private iCal URL (blank = disable calendar) |
| `calendar.poll.minutes` | `15` | Calendar poll interval |
| `max_consecutive_partials` | `60` | Force a full refresh after this many partials |
| `forced_full_refresh_minutes` | `30` | Wall-clock anti-ghosting interval |
| `partial_area_max_fraction` | `0.50` | If changed area exceeds this fraction, do a full refresh instead |
| `deep_clean_hour` / `deep_clean_minute` | `3` / `30` | Daily deep-clean time |
| `wifi.ssid` / `wifi.password` | — | Optional WiFi to bring up via the panel |

> **Do not commit real secrets.** `gradle.properties` should hold defaults only. Put your WiFi password and your calendar's private iCal URL in an untracked file and keep them out of version control. To get the calendar URL: Google Calendar → Settings → *(your calendar)* → **Secret address in iCal format**.

## Building

Requirements: Android SDK with API 34, JDK 11.

```bash
git clone https://github.com/FSA123/DeskDashboard.git
cd DeskDashboard
# set your values in gradle.properties (location, calendar URL, etc.)
./gradlew assembleDebug
```

The build targets `minSdk 24` / `targetSdk 34`, namespace `ro.deskdash.epd`. Dependencies: AppCompat, Material, and OkHttp.

## Installing

Sideload the APK onto the device and grant it the ability to run as a foreground service. It auto-starts on boot; to launch manually, open **Desk Dashboard** from the launcher once — the service takes over from there.

```bash
./gradlew installDebug
# or
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Permissions

`INTERNET`, `ACCESS_NETWORK_STATE`, `ACCESS_WIFI_STATE`, `CHANGE_WIFI_STATE`, `RECEIVE_BOOT_COMPLETED`, `FOREGROUND_SERVICE`, `FOREGROUND_SERVICE_DATA_SYNC`.

## Credits

Weather and air-quality data by [Open-Meteo](https://open-meteo.com/) (CC BY 4.0). Fonts: [Inter](https://rsms.me/inter/) and [JetBrains Mono](https://www.jetbrains.com/lp/mono/).
