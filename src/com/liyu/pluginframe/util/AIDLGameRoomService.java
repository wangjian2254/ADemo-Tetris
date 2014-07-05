/*___Generated_by_IDEA___*/

/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/wangjian2254/work/javaworkspace2/ADemo-Tetris/src/com/liyu/pluginframe/util/AIDLGameRoomService.aidl
 */
package com.liyu.pluginframe.util;
/**
 * Created by wangjian2254 on 14-6-29.
 */
public interface AIDLGameRoomService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements AIDLGameRoomService
{
private static final String DESCRIPTOR = "com.liyu.pluginframe.util.AIDLGameRoomService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.liyu.pluginframe.util.AIDLGameRoomService interface,
 * generating a proxy if needed.
 */
public static AIDLGameRoomService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof AIDLGameRoomService))) {
return ((AIDLGameRoomService)iin);
}
return new AIDLGameRoomService.Stub.Proxy(obj);
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
case TRANSACTION_init:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
this.init(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_addCB:
{
data.enforceInterface(DESCRIPTOR);
ICallBack _arg0;
_arg0 = ICallBack.Stub.asInterface(data.readStrongBinder());
this.addCB(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_delCB:
{
data.enforceInterface(DESCRIPTOR);
ICallBack _arg0;
_arg0 = ICallBack.Stub.asInterface(data.readStrongBinder());
this.delCB(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_addRoomList:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
String _arg1;
_arg1 = data.readString();
String _arg2;
_arg2 = data.readString();
this.addRoomList(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_queryRoomList:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.queryRoomList(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_quiteRoomList:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
this.quiteRoomList(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getMembersByRoom:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
String _arg1;
_arg1 = data.readString();
this.getMembersByRoom(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getRoomInfoByRoomId:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
String _arg1;
_arg1 = data.readString();
this.getRoomInfoByRoomId(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_addRoom:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
String _arg1;
_arg1 = data.readString();
String _arg2;
_arg2 = data.readString();
this.addRoom(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_quiteRoom:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
String _arg1;
_arg1 = data.readString();
String _arg2;
_arg2 = data.readString();
this.quiteRoom(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_uploadPoint:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
String _arg1;
_arg1 = data.readString();
String _arg2;
_arg2 = data.readString();
String _arg3;
_arg3 = data.readString();
this.uploadPoint(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_uploadEndPoint:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
String _arg1;
_arg1 = data.readString();
String _arg2;
_arg2 = data.readString();
String _arg3;
_arg3 = data.readString();
this.uploadEndPoint(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_cleanPoint:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
String _arg1;
_arg1 = data.readString();
String _arg2;
_arg2 = data.readString();
this.cleanPoint(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements AIDLGameRoomService
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
public String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void init(String appcode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(appcode);
mRemote.transact(Stub.TRANSACTION_init, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addCB(ICallBack icallback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((icallback!=null))?(icallback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addCB, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void delCB(ICallBack icallback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((icallback!=null))?(icallback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_delCB, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addRoomList(String appcode, String username, String userinfo) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(appcode);
_data.writeString(username);
_data.writeString(userinfo);
mRemote.transact(Stub.TRANSACTION_addRoomList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void queryRoomList(String appcode, int start, int limit) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(appcode);
_data.writeInt(start);
_data.writeInt(limit);
mRemote.transact(Stub.TRANSACTION_queryRoomList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void quiteRoomList(String appcode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(appcode);
mRemote.transact(Stub.TRANSACTION_quiteRoomList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getMembersByRoom(String appcode, String roomid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(appcode);
_data.writeString(roomid);
mRemote.transact(Stub.TRANSACTION_getMembersByRoom, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getRoomInfoByRoomId(String appcode, String roomid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(appcode);
_data.writeString(roomid);
mRemote.transact(Stub.TRANSACTION_getRoomInfoByRoomId, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addRoom(String appcode, String roomid, String username) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(appcode);
_data.writeString(roomid);
_data.writeString(username);
mRemote.transact(Stub.TRANSACTION_addRoom, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void quiteRoom(String appcode, String roomid, String username) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(appcode);
_data.writeString(roomid);
_data.writeString(username);
mRemote.transact(Stub.TRANSACTION_quiteRoom, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void uploadPoint(String appcode, String roomid, String username, String point) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(appcode);
_data.writeString(roomid);
_data.writeString(username);
_data.writeString(point);
mRemote.transact(Stub.TRANSACTION_uploadPoint, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void uploadEndPoint(String appcode, String roomid, String username, String point) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(appcode);
_data.writeString(roomid);
_data.writeString(username);
_data.writeString(point);
mRemote.transact(Stub.TRANSACTION_uploadEndPoint, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void cleanPoint(String appcode, String roomid, String username) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(appcode);
_data.writeString(roomid);
_data.writeString(username);
mRemote.transact(Stub.TRANSACTION_cleanPoint, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_init = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_addCB = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_delCB = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_addRoomList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_queryRoomList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_quiteRoomList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getMembersByRoom = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getRoomInfoByRoomId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_addRoom = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_quiteRoom = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_uploadPoint = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_uploadEndPoint = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_cleanPoint = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
}
public void init(String appcode) throws android.os.RemoteException;
public void addCB(ICallBack icallback) throws android.os.RemoteException;
public void delCB(ICallBack icallback) throws android.os.RemoteException;
public void addRoomList(String appcode, String username, String userinfo) throws android.os.RemoteException;
public void queryRoomList(String appcode, int start, int limit) throws android.os.RemoteException;
public void quiteRoomList(String appcode) throws android.os.RemoteException;
public void getMembersByRoom(String appcode, String roomid) throws android.os.RemoteException;
public void getRoomInfoByRoomId(String appcode, String roomid) throws android.os.RemoteException;
public void addRoom(String appcode, String roomid, String username) throws android.os.RemoteException;
public void quiteRoom(String appcode, String roomid, String username) throws android.os.RemoteException;
public void uploadPoint(String appcode, String roomid, String username, String point) throws android.os.RemoteException;
public void uploadEndPoint(String appcode, String roomid, String username, String point) throws android.os.RemoteException;
public void cleanPoint(String appcode, String roomid, String username) throws android.os.RemoteException;
}
