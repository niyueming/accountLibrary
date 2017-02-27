/*
 * Copyright (c) 2017  Ni YueMing<niyueming@163.com>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package net.nym.accountlibrary;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;

import static android.Manifest.permission.GET_ACCOUNTS;

/**
 * @author niyueming
 * @date 2017-02-24
 * @time 09:42
 */

public class AccountUtils {

    public static AccountManager getAccountManager(Context context) {
        return AccountManager.get(context);
    }

    /**
     * 获取当前设备所有的账户信息
     * 需要检测获取权限 {@link android.Manifest.permission#GET_ACCOUNTS}
     * @param context
     * @return
     */
    @RequiresPermission(GET_ACCOUNTS)
    public static Account[] getAccounts(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        try {
            return accountManager.getAccounts();
        }catch (SecurityException e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取自己或者特定的账户信息
     * 需要检测获取权限 {@link android.Manifest.permission#GET_ACCOUNTS}
     * @param context
     * @param type
     * @return
     */
    @RequiresPermission(GET_ACCOUNTS)
    public static Account[] getAccountsByType(Context context,String type) {
        AccountManager accountManager = AccountManager.get(context);
        try {
            return accountManager.getAccountsByType(type);
        }catch (SecurityException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加账户
     * 需要检测获取权限 {@link android.Manifest.permission#GET_ACCOUNTS}
     * @param context
     * @param type
     * @return
     */
    public static boolean addAccount(Context context, String type, String name, String password,@Nullable Bundle userData){
        AccountManager accountManager = AccountManager.get(context);
        Account account = new Account(name,type);
        return accountManager.addAccountExplicitly(account,password,userData);
    }

    /**
     * 不同设备之间同步数据
     *
     * @param account
     * @param authority 例：{@link ContactsContract#AUTHORITY} 同步通讯录
     * @param extras
     */
    public static void requestSync(Account account, String authority, Bundle extras){
        ContentResolver.requestSync(account, authority, extras);
    }

    /**
     * 令牌token失效时调用该方法
     * @param context
     * @param accountType
     * @param authToken
     */
    public static void invalidateAuthToken(Context context,final String accountType, final String authToken){
        AccountManager accountManager = AccountManager.get(context);
        accountManager.invalidateAuthToken(accountType,authToken);
    }
}
