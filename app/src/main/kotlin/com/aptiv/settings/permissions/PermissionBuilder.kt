package com.aptiv.settings.permissions

class PermissionBuilder {
    /**
     * 权限允许时回调
     */
    var granted: (permission:String) -> Unit = {}

    /**
     * 权限拒绝时回调
     */
    var denied: (permission:String) -> Unit = {}

    fun granted(callback:(permission:String) -> Unit){
        this.granted=callback
    }

    fun denied(callback:(permission:String) -> Unit){
        this.denied=callback
    }
}