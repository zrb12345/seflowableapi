package com.se.common.exception.user;

import com.se.common.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author se
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
