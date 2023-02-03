package com.star.starlight.ui.view.ext

import java.math.BigDecimal

/**
 * 万亿
 */
const val NUMBER_UNIT_TRILLION = 1000000000000L;
/**
 * 千亿
 */

const val NUMBER_UNIT_THOUSANDBILLION = 100000000000L;

/**
 * 百亿
 */
const val NUMBER_UNIT_HUNDREDBILLION = 10000000000L;

/**
 * 十亿
 */
const val NUMBER_UNIT_TENBILLION = 1000000000L;

/**
 * 亿
 */
const val NUMBER_UNIT_BILLION = 100000000L;
/**
 * 千万
 */
const val NUMBER_UNIT_TENMILLION = 10000000L;
/**
 * 百万
 */
const val NUMBER_UNIT_MILLION=1000000L;
/**
 * 十万
 */
const val NUMBER_UNIT_ONEHUNDREDTHOUSAND = 100000L
/**
 * 万
 */
const val NUMBER_UNIT_TENTHOUSAND = 10000L
/**
 * 千位
 */
const val NUMBER_UNIT_THOUSAND = 1000L;
/**
 * 百位
 */
const val NUMBER_UNIT_HUNDRED = 100L;
/**
 * 十位
 */
const val NUMBER_UNIT_TEN = 10L;
/**
 * 个位
 */
const val NUMBER_UNIT_ONE = 1L;

fun Long.formatText(minUnit : Long = 1,scan : Int = 0): String {
    if(minUnit == 0L){
        return this.toString();
    }
    val thisBig = BigDecimal(this);
    val unitBig = BigDecimal(minUnit);
    if(scan == 0){
        val r = thisBig.divide(unitBig,BigDecimal.ROUND_HALF_UP);
        return r.toString();
    }else{
        val r = thisBig.divide(unitBig,scan,BigDecimal.ROUND_HALF_UP);
        return r.toString();
    }
}

fun Long.formatChineseText(unitText : String,scan : Int = 0): String {
    if(this < NUMBER_UNIT_THOUSAND){
        return "${this}${unitText}";
    }
    var resNumber : Long = this;
    if(this >= NUMBER_UNIT_THOUSAND && this < NUMBER_UNIT_TENTHOUSAND ){
        return "${this.formatText(NUMBER_UNIT_TENTHOUSAND,scan)}千${unitText}" ;
    }
    if(this >= NUMBER_UNIT_TENTHOUSAND && this < NUMBER_UNIT_ONEHUNDREDTHOUSAND){
        return "${this.formatText(NUMBER_UNIT_TENTHOUSAND,scan)}万${unitText}";
    }
    if(this >= NUMBER_UNIT_ONEHUNDREDTHOUSAND && this < NUMBER_UNIT_MILLION){
        return "${this.formatText(NUMBER_UNIT_TENTHOUSAND,scan)}万${unitText}";
    }
    if(this >= NUMBER_UNIT_MILLION && this < NUMBER_UNIT_TENMILLION){
        return "${this.formatText(NUMBER_UNIT_TENTHOUSAND,scan)}万${unitText}";
    }
    if(this >= NUMBER_UNIT_TENMILLION && this < NUMBER_UNIT_BILLION){
        return "${this.formatText(NUMBER_UNIT_TENTHOUSAND,scan)}万${unitText}";
    }
    if(this >= NUMBER_UNIT_BILLION && this < NUMBER_UNIT_TENBILLION){
        return "${this.formatText(NUMBER_UNIT_BILLION,scan)}亿${unitText}";
    }
    if(this >= NUMBER_UNIT_TENBILLION && this < NUMBER_UNIT_HUNDREDBILLION){
        return "${this.formatText(NUMBER_UNIT_BILLION,scan)}亿${unitText}";
    }
    if(this >= NUMBER_UNIT_HUNDREDBILLION && this < NUMBER_UNIT_THOUSANDBILLION){
        return "${this.formatText(NUMBER_UNIT_BILLION,scan)}亿${unitText}";
    }
    if(this >= NUMBER_UNIT_THOUSANDBILLION && this < NUMBER_UNIT_TRILLION){
        return "${this.formatText(NUMBER_UNIT_BILLION,scan)}亿${unitText}";
    }
    return "${this.formatText(NUMBER_UNIT_BILLION,scan)}亿${unitText}";
}
