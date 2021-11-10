package com.ondev.getthiefsms.utils

fun List<String>.phoneList2String(): String {
    return this.toString().replace("[", "")
        .replace("]", "").replace(", ", "\n")
}