package com.star.starlight.ui.view.ext

/**
 * 手机号码加星号处理
 * create by Eastevil at 2022/11/1 14:39
 * @author Eastevil
 * @return
 *      带 * 号的手机号码文本，格式[xxx **** xxxx]
 */
fun String?.mobileAsterisk(): String {
    if(this.isNullOrEmpty() || this.length <= 4){
        return ""
    }
    val s = "${this.subSequence(0,3)} **** ${this.subSequence(this.length-4,this.length)}";
    return s;
}
