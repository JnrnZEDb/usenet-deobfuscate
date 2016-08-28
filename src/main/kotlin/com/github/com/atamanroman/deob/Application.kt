package com.github.com.atamanroman.deob


fun main(args: Array<String>) {
    val flow = ProgramFlow(args[0])
    flow.go()
    println(flow.detected)
}
