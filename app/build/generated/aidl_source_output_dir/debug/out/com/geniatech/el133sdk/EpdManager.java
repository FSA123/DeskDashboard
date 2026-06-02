/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Using: C:\Users\Gigel\AppData\Local\Android\Sdk\build-tools\35.0.0\aidl.exe -pC:\Users\Gigel\AppData\Local\Android\Sdk\platforms\android-34\framework.aidl -oC:\Users\Gigel\Desktop\Filip\EPD-COLOR\DeskDashboard\app\build\generated\aidl_source_output_dir\debug\out -IC:\Users\Gigel\Desktop\Filip\EPD-COLOR\DeskDashboard\app\src\main\aidl -IC:\Users\Gigel\Desktop\Filip\EPD-COLOR\DeskDashboard\app\src\debug\aidl -IC:\Users\Gigel\.gradle\caches\8.13\transforms\04879d85d82a251a245929a580a1720d\transformed\core-1.9.0\aidl -IC:\Users\Gigel\.gradle\caches\8.13\transforms\4a811471ef6f84b7c8adf85be307d7a8\transformed\versionedparcelable-1.1.1\aidl -dC:\Users\Gigel\AppData\Local\Temp\aidl4342268533190378212.d C:\Users\Gigel\Desktop\Filip\EPD-COLOR\DeskDashboard\app\src\main\aidl\com\geniatech\el133sdk\EpdManager.aidl
 */
package com.geniatech.el133sdk;
public interface EpdManager extends android.os.IInterface
{
  /** Default implementation for EpdManager. */
  public static class Default implements com.geniatech.el133sdk.EpdManager
  {
    @Override public java.lang.String getEPDInfo() throws android.os.RemoteException
    {
      return null;
    }
    @Override public int sendImage(java.lang.String imagePath) throws android.os.RemoteException
    {
      return 0;
    }
    @Override public int sendImageByNum(java.lang.String imagePath, int num) throws android.os.RemoteException
    {
      return 0;
    }
    @Override public int sendImageAddMagic(java.lang.String imagePath, java.lang.String magicPath) throws android.os.RemoteException
    {
      return 0;
    }
    @Override public int sendImageBitmap(android.graphics.Bitmap bitmap) throws android.os.RemoteException
    {
      return 0;
    }
    @Override public int sendImageWithDetails(java.lang.String imagePath, int x, int y, int w, int h, double hue_offset, double sat_offset, double bright_offset, double contrast_offset, double gamma) throws android.os.RemoteException
    {
      return 0;
    }
    @Override public void goToSleep(int sleepSec) throws android.os.RemoteException
    {
    }
    @Override public void setOSReboot() throws android.os.RemoteException
    {
    }
    @Override public void setOSRebootTime(int rebootTime) throws android.os.RemoteException
    {
    }
    @Override public int getTCONTemperature() throws android.os.RemoteException
    {
      return 0;
    }
    @Override public void upgradeTCON(java.lang.String path) throws android.os.RemoteException
    {
    }
    @Override public java.lang.String getServiceVersion() throws android.os.RemoteException
    {
      return null;
    }
    @Override public void setEPDScreenRotate(int degree) throws android.os.RemoteException
    {
    }
    @Override public int getEPDScreenRotate() throws android.os.RemoteException
    {
      return 0;
    }
    @Override public void setLedOn(int brightness) throws android.os.RemoteException
    {
    }
    @Override public void setLedOff() throws android.os.RemoteException
    {
    }
    @Override public void setWifiOn() throws android.os.RemoteException
    {
    }
    @Override public void setWifiOff() throws android.os.RemoteException
    {
    }
    @Override public void forgetWifi() throws android.os.RemoteException
    {
    }
    @Override public java.lang.String getWifiCountryCode() throws android.os.RemoteException
    {
      return null;
    }
    @Override public void setWifiCountryCode(java.lang.String countryCode) throws android.os.RemoteException
    {
    }
    @Override public void setHotspotOn() throws android.os.RemoteException
    {
    }
    @Override public void setHotspotOff() throws android.os.RemoteException
    {
    }
    @Override public java.lang.String getSerialNumber() throws android.os.RemoteException
    {
      return null;
    }
    @Override public java.lang.String getBuildNumber() throws android.os.RemoteException
    {
      return null;
    }
    @Override public void setNTPServer(java.lang.String ntpServer) throws android.os.RemoteException
    {
    }
    @Override public java.lang.String getNTPServer() throws android.os.RemoteException
    {
      return null;
    }
    @Override public void setTimeZone(java.lang.String timeZone) throws android.os.RemoteException
    {
    }
    @Override public void setSystemTime(int year, int month, int day, int hour, int minute, int second) throws android.os.RemoteException
    {
    }
    @Override public void installAPK(java.lang.String path, java.lang.String apkName, java.lang.String pkgName) throws android.os.RemoteException
    {
    }
    @Override public void isShowNavigationBar(boolean isshow) throws android.os.RemoteException
    {
    }
    @Override public void isShowStatusBar(boolean isshow) throws android.os.RemoteException
    {
    }
    @Override public int sendpartImage(java.lang.String imagePath, int x, int y) throws android.os.RemoteException
    {
      return 0;
    }
    @Override public int sendpartImageAddMagic(java.lang.String imagePath, java.lang.String magicPath, int x, int y) throws android.os.RemoteException
    {
      return 0;
    }
    @Override public int sendpartImageWithMask(java.lang.String imagePath, java.lang.String MaskImagePath) throws android.os.RemoteException
    {
      return 0;
    }
    @Override public int sendpartImageWithMaskAddMagic(java.lang.String imagePath, java.lang.String MaskImagePath, java.lang.String magicPath) throws android.os.RemoteException
    {
      return 0;
    }
    @Override public int sendpartImageBitmap(android.graphics.Bitmap bitmap, int x, int y) throws android.os.RemoteException
    {
      return 0;
    }
    @Override public int screenshot(java.lang.String outputImgPath) throws android.os.RemoteException
    {
      return 0;
    }
    @Override public void setAutoRefrushTime(int time) throws android.os.RemoteException
    {
    }
    @Override public void isOpenRefrushTime(boolean isopen) throws android.os.RemoteException
    {
    }
    @Override public void fwUpgrade(java.lang.String path) throws android.os.RemoteException
    {
    }
    @Override public void clScr() throws android.os.RemoteException
    {
    }
    @Override public void setDisplayMode(int mode) throws android.os.RemoteException
    {
    }
    @Override public int sendStream(java.lang.String streamPath) throws android.os.RemoteException
    {
      return 0;
    }
    @Override public int getBatteryLevel() throws android.os.RemoteException
    {
      return 0;
    }
    @Override public void setEPDRippeMode(boolean isRippe) throws android.os.RemoteException
    {
    }
    @Override public boolean getEPDRippeMode() throws android.os.RemoteException
    {
      return false;
    }
    @Override public void setImageAdjustment(double hue_offset, double sat_offset, double bright_offset, double contrast_offset, double gamma) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.geniatech.el133sdk.EpdManager
  {
    /** Construct the stub at attach it to the interface. */
    @SuppressWarnings("this-escape")
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.geniatech.el133sdk.EpdManager interface,
     * generating a proxy if needed.
     */
    public static com.geniatech.el133sdk.EpdManager asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.geniatech.el133sdk.EpdManager))) {
        return ((com.geniatech.el133sdk.EpdManager)iin);
      }
      return new com.geniatech.el133sdk.EpdManager.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      if (code >= android.os.IBinder.FIRST_CALL_TRANSACTION && code <= android.os.IBinder.LAST_CALL_TRANSACTION) {
        data.enforceInterface(descriptor);
      }
      if (code == INTERFACE_TRANSACTION) {
        reply.writeString(descriptor);
        return true;
      }
      switch (code)
      {
        case TRANSACTION_getEPDInfo:
        {
          java.lang.String _result = this.getEPDInfo();
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_sendImage:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          int _result = this.sendImage(_arg0);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_sendImageByNum:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          int _arg1;
          _arg1 = data.readInt();
          int _result = this.sendImageByNum(_arg0, _arg1);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_sendImageAddMagic:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          int _result = this.sendImageAddMagic(_arg0, _arg1);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_sendImageBitmap:
        {
          android.graphics.Bitmap _arg0;
          _arg0 = _Parcel.readTypedObject(data, android.graphics.Bitmap.CREATOR);
          int _result = this.sendImageBitmap(_arg0);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_sendImageWithDetails:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          int _arg3;
          _arg3 = data.readInt();
          int _arg4;
          _arg4 = data.readInt();
          double _arg5;
          _arg5 = data.readDouble();
          double _arg6;
          _arg6 = data.readDouble();
          double _arg7;
          _arg7 = data.readDouble();
          double _arg8;
          _arg8 = data.readDouble();
          double _arg9;
          _arg9 = data.readDouble();
          int _result = this.sendImageWithDetails(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_goToSleep:
        {
          int _arg0;
          _arg0 = data.readInt();
          this.goToSleep(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_setOSReboot:
        {
          this.setOSReboot();
          reply.writeNoException();
          break;
        }
        case TRANSACTION_setOSRebootTime:
        {
          int _arg0;
          _arg0 = data.readInt();
          this.setOSRebootTime(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_getTCONTemperature:
        {
          int _result = this.getTCONTemperature();
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_upgradeTCON:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.upgradeTCON(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_getServiceVersion:
        {
          java.lang.String _result = this.getServiceVersion();
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_setEPDScreenRotate:
        {
          int _arg0;
          _arg0 = data.readInt();
          this.setEPDScreenRotate(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_getEPDScreenRotate:
        {
          int _result = this.getEPDScreenRotate();
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_setLedOn:
        {
          int _arg0;
          _arg0 = data.readInt();
          this.setLedOn(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_setLedOff:
        {
          this.setLedOff();
          reply.writeNoException();
          break;
        }
        case TRANSACTION_setWifiOn:
        {
          this.setWifiOn();
          reply.writeNoException();
          break;
        }
        case TRANSACTION_setWifiOff:
        {
          this.setWifiOff();
          reply.writeNoException();
          break;
        }
        case TRANSACTION_forgetWifi:
        {
          this.forgetWifi();
          reply.writeNoException();
          break;
        }
        case TRANSACTION_getWifiCountryCode:
        {
          java.lang.String _result = this.getWifiCountryCode();
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_setWifiCountryCode:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.setWifiCountryCode(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_setHotspotOn:
        {
          this.setHotspotOn();
          reply.writeNoException();
          break;
        }
        case TRANSACTION_setHotspotOff:
        {
          this.setHotspotOff();
          reply.writeNoException();
          break;
        }
        case TRANSACTION_getSerialNumber:
        {
          java.lang.String _result = this.getSerialNumber();
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_getBuildNumber:
        {
          java.lang.String _result = this.getBuildNumber();
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_setNTPServer:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.setNTPServer(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_getNTPServer:
        {
          java.lang.String _result = this.getNTPServer();
          reply.writeNoException();
          reply.writeString(_result);
          break;
        }
        case TRANSACTION_setTimeZone:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.setTimeZone(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_setSystemTime:
        {
          int _arg0;
          _arg0 = data.readInt();
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          int _arg3;
          _arg3 = data.readInt();
          int _arg4;
          _arg4 = data.readInt();
          int _arg5;
          _arg5 = data.readInt();
          this.setSystemTime(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_installAPK:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          java.lang.String _arg2;
          _arg2 = data.readString();
          this.installAPK(_arg0, _arg1, _arg2);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_isShowNavigationBar:
        {
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          this.isShowNavigationBar(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_isShowStatusBar:
        {
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          this.isShowStatusBar(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_sendpartImage:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          int _result = this.sendpartImage(_arg0, _arg1, _arg2);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_sendpartImageAddMagic:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          int _arg2;
          _arg2 = data.readInt();
          int _arg3;
          _arg3 = data.readInt();
          int _result = this.sendpartImageAddMagic(_arg0, _arg1, _arg2, _arg3);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_sendpartImageWithMask:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          int _result = this.sendpartImageWithMask(_arg0, _arg1);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_sendpartImageWithMaskAddMagic:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          java.lang.String _arg1;
          _arg1 = data.readString();
          java.lang.String _arg2;
          _arg2 = data.readString();
          int _result = this.sendpartImageWithMaskAddMagic(_arg0, _arg1, _arg2);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_sendpartImageBitmap:
        {
          android.graphics.Bitmap _arg0;
          _arg0 = _Parcel.readTypedObject(data, android.graphics.Bitmap.CREATOR);
          int _arg1;
          _arg1 = data.readInt();
          int _arg2;
          _arg2 = data.readInt();
          int _result = this.sendpartImageBitmap(_arg0, _arg1, _arg2);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_screenshot:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          int _result = this.screenshot(_arg0);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_setAutoRefrushTime:
        {
          int _arg0;
          _arg0 = data.readInt();
          this.setAutoRefrushTime(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_isOpenRefrushTime:
        {
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          this.isOpenRefrushTime(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_fwUpgrade:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.fwUpgrade(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_clScr:
        {
          this.clScr();
          reply.writeNoException();
          break;
        }
        case TRANSACTION_setDisplayMode:
        {
          int _arg0;
          _arg0 = data.readInt();
          this.setDisplayMode(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_sendStream:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          int _result = this.sendStream(_arg0);
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_getBatteryLevel:
        {
          int _result = this.getBatteryLevel();
          reply.writeNoException();
          reply.writeInt(_result);
          break;
        }
        case TRANSACTION_setEPDRippeMode:
        {
          boolean _arg0;
          _arg0 = (0!=data.readInt());
          this.setEPDRippeMode(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_getEPDRippeMode:
        {
          boolean _result = this.getEPDRippeMode();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          break;
        }
        case TRANSACTION_setImageAdjustment:
        {
          double _arg0;
          _arg0 = data.readDouble();
          double _arg1;
          _arg1 = data.readDouble();
          double _arg2;
          _arg2 = data.readDouble();
          double _arg3;
          _arg3 = data.readDouble();
          double _arg4;
          _arg4 = data.readDouble();
          this.setImageAdjustment(_arg0, _arg1, _arg2, _arg3, _arg4);
          reply.writeNoException();
          break;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
      return true;
    }
    private static class Proxy implements com.geniatech.el133sdk.EpdManager
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      @Override public java.lang.String getEPDInfo() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getEPDInfo, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public int sendImage(java.lang.String imagePath) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(imagePath);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendImage, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public int sendImageByNum(java.lang.String imagePath, int num) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(imagePath);
          _data.writeInt(num);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendImageByNum, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public int sendImageAddMagic(java.lang.String imagePath, java.lang.String magicPath) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(imagePath);
          _data.writeString(magicPath);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendImageAddMagic, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public int sendImageBitmap(android.graphics.Bitmap bitmap) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _Parcel.writeTypedObject(_data, bitmap, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendImageBitmap, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public int sendImageWithDetails(java.lang.String imagePath, int x, int y, int w, int h, double hue_offset, double sat_offset, double bright_offset, double contrast_offset, double gamma) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(imagePath);
          _data.writeInt(x);
          _data.writeInt(y);
          _data.writeInt(w);
          _data.writeInt(h);
          _data.writeDouble(hue_offset);
          _data.writeDouble(sat_offset);
          _data.writeDouble(bright_offset);
          _data.writeDouble(contrast_offset);
          _data.writeDouble(gamma);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendImageWithDetails, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void goToSleep(int sleepSec) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(sleepSec);
          boolean _status = mRemote.transact(Stub.TRANSACTION_goToSleep, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void setOSReboot() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setOSReboot, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void setOSRebootTime(int rebootTime) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(rebootTime);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setOSRebootTime, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public int getTCONTemperature() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getTCONTemperature, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void upgradeTCON(java.lang.String path) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(path);
          boolean _status = mRemote.transact(Stub.TRANSACTION_upgradeTCON, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public java.lang.String getServiceVersion() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getServiceVersion, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void setEPDScreenRotate(int degree) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(degree);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setEPDScreenRotate, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public int getEPDScreenRotate() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getEPDScreenRotate, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void setLedOn(int brightness) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(brightness);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setLedOn, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void setLedOff() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setLedOff, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void setWifiOn() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setWifiOn, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void setWifiOff() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setWifiOff, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void forgetWifi() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_forgetWifi, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public java.lang.String getWifiCountryCode() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getWifiCountryCode, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void setWifiCountryCode(java.lang.String countryCode) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(countryCode);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setWifiCountryCode, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void setHotspotOn() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setHotspotOn, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void setHotspotOff() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setHotspotOff, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public java.lang.String getSerialNumber() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getSerialNumber, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public java.lang.String getBuildNumber() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getBuildNumber, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void setNTPServer(java.lang.String ntpServer) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(ntpServer);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setNTPServer, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public java.lang.String getNTPServer() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.lang.String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getNTPServer, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void setTimeZone(java.lang.String timeZone) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(timeZone);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setTimeZone, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void setSystemTime(int year, int month, int day, int hour, int minute, int second) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(year);
          _data.writeInt(month);
          _data.writeInt(day);
          _data.writeInt(hour);
          _data.writeInt(minute);
          _data.writeInt(second);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setSystemTime, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void installAPK(java.lang.String path, java.lang.String apkName, java.lang.String pkgName) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(path);
          _data.writeString(apkName);
          _data.writeString(pkgName);
          boolean _status = mRemote.transact(Stub.TRANSACTION_installAPK, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void isShowNavigationBar(boolean isshow) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((isshow)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_isShowNavigationBar, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void isShowStatusBar(boolean isshow) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((isshow)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_isShowStatusBar, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public int sendpartImage(java.lang.String imagePath, int x, int y) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(imagePath);
          _data.writeInt(x);
          _data.writeInt(y);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendpartImage, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public int sendpartImageAddMagic(java.lang.String imagePath, java.lang.String magicPath, int x, int y) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(imagePath);
          _data.writeString(magicPath);
          _data.writeInt(x);
          _data.writeInt(y);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendpartImageAddMagic, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public int sendpartImageWithMask(java.lang.String imagePath, java.lang.String MaskImagePath) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(imagePath);
          _data.writeString(MaskImagePath);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendpartImageWithMask, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public int sendpartImageWithMaskAddMagic(java.lang.String imagePath, java.lang.String MaskImagePath, java.lang.String magicPath) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(imagePath);
          _data.writeString(MaskImagePath);
          _data.writeString(magicPath);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendpartImageWithMaskAddMagic, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public int sendpartImageBitmap(android.graphics.Bitmap bitmap, int x, int y) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _Parcel.writeTypedObject(_data, bitmap, 0);
          _data.writeInt(x);
          _data.writeInt(y);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendpartImageBitmap, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public int screenshot(java.lang.String outputImgPath) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(outputImgPath);
          boolean _status = mRemote.transact(Stub.TRANSACTION_screenshot, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void setAutoRefrushTime(int time) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(time);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setAutoRefrushTime, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void isOpenRefrushTime(boolean isopen) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((isopen)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_isOpenRefrushTime, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void fwUpgrade(java.lang.String path) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(path);
          boolean _status = mRemote.transact(Stub.TRANSACTION_fwUpgrade, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void clScr() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_clScr, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void setDisplayMode(int mode) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(mode);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setDisplayMode, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public int sendStream(java.lang.String streamPath) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(streamPath);
          boolean _status = mRemote.transact(Stub.TRANSACTION_sendStream, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public int getBatteryLevel() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getBatteryLevel, _data, _reply, 0);
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void setEPDRippeMode(boolean isRippe) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(((isRippe)?(1):(0)));
          boolean _status = mRemote.transact(Stub.TRANSACTION_setEPDRippeMode, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public boolean getEPDRippeMode() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getEPDRippeMode, _data, _reply, 0);
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void setImageAdjustment(double hue_offset, double sat_offset, double bright_offset, double contrast_offset, double gamma) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeDouble(hue_offset);
          _data.writeDouble(sat_offset);
          _data.writeDouble(bright_offset);
          _data.writeDouble(contrast_offset);
          _data.writeDouble(gamma);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setImageAdjustment, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }
    static final int TRANSACTION_getEPDInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_sendImage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_sendImageByNum = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_sendImageAddMagic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_sendImageBitmap = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_sendImageWithDetails = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    static final int TRANSACTION_goToSleep = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
    static final int TRANSACTION_setOSReboot = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
    static final int TRANSACTION_setOSRebootTime = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
    static final int TRANSACTION_getTCONTemperature = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
    static final int TRANSACTION_upgradeTCON = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
    static final int TRANSACTION_getServiceVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
    static final int TRANSACTION_setEPDScreenRotate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
    static final int TRANSACTION_getEPDScreenRotate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
    static final int TRANSACTION_setLedOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
    static final int TRANSACTION_setLedOff = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
    static final int TRANSACTION_setWifiOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
    static final int TRANSACTION_setWifiOff = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
    static final int TRANSACTION_forgetWifi = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
    static final int TRANSACTION_getWifiCountryCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
    static final int TRANSACTION_setWifiCountryCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
    static final int TRANSACTION_setHotspotOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
    static final int TRANSACTION_setHotspotOff = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
    static final int TRANSACTION_getSerialNumber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
    static final int TRANSACTION_getBuildNumber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
    static final int TRANSACTION_setNTPServer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
    static final int TRANSACTION_getNTPServer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
    static final int TRANSACTION_setTimeZone = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
    static final int TRANSACTION_setSystemTime = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
    static final int TRANSACTION_installAPK = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
    static final int TRANSACTION_isShowNavigationBar = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
    static final int TRANSACTION_isShowStatusBar = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
    static final int TRANSACTION_sendpartImage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
    static final int TRANSACTION_sendpartImageAddMagic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
    static final int TRANSACTION_sendpartImageWithMask = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
    static final int TRANSACTION_sendpartImageWithMaskAddMagic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
    static final int TRANSACTION_sendpartImageBitmap = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
    static final int TRANSACTION_screenshot = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
    static final int TRANSACTION_setAutoRefrushTime = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
    static final int TRANSACTION_isOpenRefrushTime = (android.os.IBinder.FIRST_CALL_TRANSACTION + 39);
    static final int TRANSACTION_fwUpgrade = (android.os.IBinder.FIRST_CALL_TRANSACTION + 40);
    static final int TRANSACTION_clScr = (android.os.IBinder.FIRST_CALL_TRANSACTION + 41);
    static final int TRANSACTION_setDisplayMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 42);
    static final int TRANSACTION_sendStream = (android.os.IBinder.FIRST_CALL_TRANSACTION + 43);
    static final int TRANSACTION_getBatteryLevel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 44);
    static final int TRANSACTION_setEPDRippeMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 45);
    static final int TRANSACTION_getEPDRippeMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 46);
    static final int TRANSACTION_setImageAdjustment = (android.os.IBinder.FIRST_CALL_TRANSACTION + 47);
  }
  /** @hide */
  public static final java.lang.String DESCRIPTOR = "com.geniatech.el133sdk.EpdManager";
  public java.lang.String getEPDInfo() throws android.os.RemoteException;
  public int sendImage(java.lang.String imagePath) throws android.os.RemoteException;
  public int sendImageByNum(java.lang.String imagePath, int num) throws android.os.RemoteException;
  public int sendImageAddMagic(java.lang.String imagePath, java.lang.String magicPath) throws android.os.RemoteException;
  public int sendImageBitmap(android.graphics.Bitmap bitmap) throws android.os.RemoteException;
  public int sendImageWithDetails(java.lang.String imagePath, int x, int y, int w, int h, double hue_offset, double sat_offset, double bright_offset, double contrast_offset, double gamma) throws android.os.RemoteException;
  public void goToSleep(int sleepSec) throws android.os.RemoteException;
  public void setOSReboot() throws android.os.RemoteException;
  public void setOSRebootTime(int rebootTime) throws android.os.RemoteException;
  public int getTCONTemperature() throws android.os.RemoteException;
  public void upgradeTCON(java.lang.String path) throws android.os.RemoteException;
  public java.lang.String getServiceVersion() throws android.os.RemoteException;
  public void setEPDScreenRotate(int degree) throws android.os.RemoteException;
  public int getEPDScreenRotate() throws android.os.RemoteException;
  public void setLedOn(int brightness) throws android.os.RemoteException;
  public void setLedOff() throws android.os.RemoteException;
  public void setWifiOn() throws android.os.RemoteException;
  public void setWifiOff() throws android.os.RemoteException;
  public void forgetWifi() throws android.os.RemoteException;
  public java.lang.String getWifiCountryCode() throws android.os.RemoteException;
  public void setWifiCountryCode(java.lang.String countryCode) throws android.os.RemoteException;
  public void setHotspotOn() throws android.os.RemoteException;
  public void setHotspotOff() throws android.os.RemoteException;
  public java.lang.String getSerialNumber() throws android.os.RemoteException;
  public java.lang.String getBuildNumber() throws android.os.RemoteException;
  public void setNTPServer(java.lang.String ntpServer) throws android.os.RemoteException;
  public java.lang.String getNTPServer() throws android.os.RemoteException;
  public void setTimeZone(java.lang.String timeZone) throws android.os.RemoteException;
  public void setSystemTime(int year, int month, int day, int hour, int minute, int second) throws android.os.RemoteException;
  public void installAPK(java.lang.String path, java.lang.String apkName, java.lang.String pkgName) throws android.os.RemoteException;
  public void isShowNavigationBar(boolean isshow) throws android.os.RemoteException;
  public void isShowStatusBar(boolean isshow) throws android.os.RemoteException;
  public int sendpartImage(java.lang.String imagePath, int x, int y) throws android.os.RemoteException;
  public int sendpartImageAddMagic(java.lang.String imagePath, java.lang.String magicPath, int x, int y) throws android.os.RemoteException;
  public int sendpartImageWithMask(java.lang.String imagePath, java.lang.String MaskImagePath) throws android.os.RemoteException;
  public int sendpartImageWithMaskAddMagic(java.lang.String imagePath, java.lang.String MaskImagePath, java.lang.String magicPath) throws android.os.RemoteException;
  public int sendpartImageBitmap(android.graphics.Bitmap bitmap, int x, int y) throws android.os.RemoteException;
  public int screenshot(java.lang.String outputImgPath) throws android.os.RemoteException;
  public void setAutoRefrushTime(int time) throws android.os.RemoteException;
  public void isOpenRefrushTime(boolean isopen) throws android.os.RemoteException;
  public void fwUpgrade(java.lang.String path) throws android.os.RemoteException;
  public void clScr() throws android.os.RemoteException;
  public void setDisplayMode(int mode) throws android.os.RemoteException;
  public int sendStream(java.lang.String streamPath) throws android.os.RemoteException;
  public int getBatteryLevel() throws android.os.RemoteException;
  public void setEPDRippeMode(boolean isRippe) throws android.os.RemoteException;
  public boolean getEPDRippeMode() throws android.os.RemoteException;
  public void setImageAdjustment(double hue_offset, double sat_offset, double bright_offset, double contrast_offset, double gamma) throws android.os.RemoteException;
  /** @hide */
  static class _Parcel {
    static private <T> T readTypedObject(
        android.os.Parcel parcel,
        android.os.Parcelable.Creator<T> c) {
      if (parcel.readInt() != 0) {
          return c.createFromParcel(parcel);
      } else {
          return null;
      }
    }
    static private <T extends android.os.Parcelable> void writeTypedObject(
        android.os.Parcel parcel, T value, int parcelableFlags) {
      if (value != null) {
        parcel.writeInt(1);
        value.writeToParcel(parcel, parcelableFlags);
      } else {
        parcel.writeInt(0);
      }
    }
  }
}
