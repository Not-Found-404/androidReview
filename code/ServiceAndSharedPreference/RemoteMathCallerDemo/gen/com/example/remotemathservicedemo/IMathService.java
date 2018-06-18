/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\AndroidSource\\RemoteMathCallerDemo\\src\\com\\example\\remotemathservicedemo\\IMathService.aidl
 */
package com.example.remotemathservicedemo;
public interface IMathService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.example.remotemathservicedemo.IMathService
{
private static final java.lang.String DESCRIPTOR = "com.example.remotemathservicedemo.IMathService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.example.remotemathservicedemo.IMathService interface,
 * generating a proxy if needed.
 */
public static com.example.remotemathservicedemo.IMathService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.example.remotemathservicedemo.IMathService))) {
return ((com.example.remotemathservicedemo.IMathService)iin);
}
return new com.example.remotemathservicedemo.IMathService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_Add:
{
data.enforceInterface(DESCRIPTOR);
long _arg0;
_arg0 = data.readLong();
long _arg1;
_arg1 = data.readLong();
long _result = this.Add(_arg0, _arg1);
reply.writeNoException();
reply.writeLong(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.example.remotemathservicedemo.IMathService
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
@Override public long Add(long a, long b) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(a);
_data.writeLong(b);
mRemote.transact(Stub.TRANSACTION_Add, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_Add = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public long Add(long a, long b) throws android.os.RemoteException;
}
