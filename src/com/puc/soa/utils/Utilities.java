package com.puc.soa.utils;

import android.content.Context;
import android.util.TypedValue;

public class Utilities 
{
	public static float dimToPx(int px, Context context)
	{
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
	}

}
