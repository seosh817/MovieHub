package com.seosh817.moviehub.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Immutable
data class Dimensions(
    val dp_1: Dp = 1.dp,
    val dp_2: Dp = 2.dp,
    val dp_3: Dp = 3.dp,
    val dp_4: Dp = 4.dp,
    val dp_5: Dp = 5.dp,
    val dp_6: Dp = 6.dp,
    val dp_7: Dp = 7.dp,
    val dp_8: Dp = 8.dp,
    val dp_9: Dp = 9.dp,
    val dp_10: Dp = 10.dp,
    val dp_12: Dp = 12.dp,
    val dp_14: Dp = 14.dp,
    val dp_16: Dp = 16.dp,
    val dp_18: Dp = 18.dp,
    val dp_20: Dp = 20.dp,
    val dp_22: Dp = 22.dp,
    val dp_24: Dp = 24.dp,
    val dp_26: Dp = 26.dp,
    val dp_28: Dp = 28.dp,
    val dp_30: Dp = 30.dp,
    val dp_32: Dp = 32.dp,
    val dp_34: Dp = 34.dp,
    val dp_36: Dp = 36.dp,
    val dp_38: Dp = 38.dp,
    val dp_40: Dp = 40.dp,
    val dp_42: Dp = 42.dp,
    val dp_44: Dp = 44.dp,
    val dp_46: Dp = 46.dp,
    val dp_48: Dp = 48.dp,
    val dp_50: Dp = 50.dp,
    val dp_52: Dp = 52.dp,
    val dp_54: Dp = 54.dp,
    val dp_56: Dp = 56.dp,
    val dp_58: Dp = 58.dp,
    val dp_60: Dp = 60.dp,
    val dp_80: Dp = 80.dp,

    val sp_1: TextUnit = 1.sp,
    val sp_2: TextUnit = 2.sp,
    val sp_3: TextUnit = 3.sp,
    val sp_4: TextUnit = 4.sp,
    val sp_5: TextUnit = 5.sp,
    val sp_6: TextUnit = 6.sp,
    val sp_7: TextUnit = 7.sp,
    val sp_8: TextUnit = 8.sp,
    val sp_9: TextUnit = 9.sp,
    val sp_10: TextUnit = 10.sp,
    val sp_12: TextUnit = 12.sp,
    val sp_14: TextUnit = 14.sp,
    val sp_16: TextUnit = 16.sp,
    val sp_18: TextUnit = 18.sp,
    val sp_20: TextUnit = 20.sp,
    val sp_22: TextUnit = 22.sp,
    val sp_24: TextUnit = 24.sp,
    val sp_26: TextUnit = 26.sp,
    val sp_28: TextUnit = 28.sp,
    val sp_30: TextUnit = 30.sp,
    val sp_32: TextUnit = 32.sp
)

val mDpiDimensions = Dimensions()

val hDpiDimensions = Dimensions(
    dp_1 = 0.6.dp,
    dp_2 = 1.33.dp,
    dp_3 = 2.dp,
    dp_4 = 2.66.dp,
    dp_5 = 3.33.dp,
    dp_6 = 4.dp,
    dp_7 = 4.66.dp,
    dp_8 = 5.33.dp,
    dp_9 = 6.dp,
    dp_10 = 6.66.dp,
    dp_12 = 8.dp,
    dp_14 = 9.33.dp,
    dp_16 = 10.66.dp,
    dp_18 = 12.dp,
    dp_20 = 13.33.dp,
    dp_22 = 14.66.dp,
    dp_24 = 16.dp,
    dp_26 = 17.33.dp,
    dp_28 = 18.66.dp,
    dp_30 = 20.dp,
    dp_32 = 21.33.dp,
    dp_34 = 22.66.dp,
    dp_36 = 24.dp,
    dp_38 = 25.33.dp,
    dp_40 = 26.66.dp,
    dp_42 = 28.dp,
    dp_44 = 29.33.dp,
    dp_46 = 30.66.dp,
    dp_48 = 32.dp,
    dp_50 = 33.33.dp,
    dp_52 = 34.66.dp,
    dp_54 = 36.dp,
    dp_56 = 37.33.dp,
    dp_58 = 38.66.dp,
    dp_60 = 40.dp,
    dp_80 = 53.33.dp,

    sp_1 = 0.6.sp,
    sp_2 = 1.33.sp,
    sp_3 = 2.sp,
    sp_4 = 2.66.sp,
    sp_5 = 3.33.sp,
    sp_6 = 4.sp,
    sp_7 = 4.66.sp,
    sp_8 = 5.33.sp,
    sp_9 = 6.sp,
    sp_10 = 6.66.sp,
    sp_12 = 8.sp,
    sp_14 = 9.33.sp,
    sp_16 = 10.66.sp,
    sp_18 = 12.sp,
    sp_20 = 13.33.sp,
    sp_22 = 14.66.sp,
    sp_24 = 16.sp,
    sp_26 = 17.33.sp,
    sp_28 = 18.66.sp,
    sp_30 = 20.sp,
    sp_32 = 21.33.sp,
)

val xhDpiDimensions = Dimensions(
    dp_1 = 0.5.dp,
    dp_2 = 1.dp,
    dp_3 = 1.5.dp,
    dp_4 = 2.dp,
    dp_5 = 2.5.dp,
    dp_6 = 3.dp,
    dp_7 = 3.5.dp,
    dp_8 = 4.dp,
    dp_9 = 4.5.dp,
    dp_10 = 5.dp,
    dp_12 = 6.dp,
    dp_14 = 7.dp,
    dp_16 = 8.dp,
    dp_18 = 9.dp,
    dp_20 = 10.dp,
    dp_22 = 11.dp,
    dp_24 = 12.dp,
    dp_26 = 13.dp,
    dp_28 = 14.dp,
    dp_30 = 15.dp,
    dp_32 = 16.dp,
    dp_34 = 17.dp,
    dp_36 = 18.dp,
    dp_38 = 19.dp,
    dp_40 = 20.dp,
    dp_42 = 21.dp,
    dp_44 = 22.dp,
    dp_46 = 23.dp,
    dp_48 = 24.dp,
    dp_50 = 25.dp,
    dp_52 = 26.dp,
    dp_54 = 27.dp,
    dp_56 = 28.dp,
    dp_58 = 29.dp,
    dp_60 = 30.dp,
    dp_80 = 40.dp,

    sp_1 = 0.5.sp,
    sp_2 = 1.sp,
    sp_3 = 1.5.sp,
    sp_4 = 2.sp,
    sp_5 = 2.5.sp,
    sp_6 = 3.sp,
    sp_7 = 3.5.sp,
    sp_8 = 4.sp,
    sp_9 = 4.5.sp,
    sp_10 = 5.sp,
    sp_12 = 6.sp,
    sp_14 = 7.sp,
    sp_16 = 8.sp,
    sp_18 = 9.sp,
    sp_20 = 10.sp,
    sp_22 = 11.sp,
    sp_24 = 12.sp,
    sp_26 = 13.sp,
    sp_28 = 14.sp,
    sp_30 = 15.sp,
    sp_32 = 16.sp,
)

val Dimens: Dimensions
    @Composable
    get() = AppTheme.dimens

val LocalAppDimens = staticCompositionLocalOf { Dimensions() }
