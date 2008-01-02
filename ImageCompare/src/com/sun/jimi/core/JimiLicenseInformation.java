/*
 * Copyright 1998 by Sun Microsystems, Inc.
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

package com.sun.jimi.core;

/**
 * Class for accessing license features information.
 * @author  Luke Gorrie
 * @version $Revision: 1.1 $ $Date: 1999/04/20 14:16:42 $
 */
public class JimiLicenseInformation
{
	public static boolean isLimited()
	{
		return Jimi.limited;
	}

	public static boolean isCrippled()
	{
		return Jimi.crippled;
	}
}

