package com.github.atamanroman.deob

import java.io.File


fun main(args: Array<String>) {
    if(args.isEmpty()) {
        error("Root path must be provided. \n    E.g.: `deob /my/sample/folder`")
        return
    }

    val dryRun = args.contains("-n")
    val argList = args.toList().minus("-n")


    if(!File(argList[0]).isDirectory) {
        error("Root path ${argList[0]} does not exist")
    }

    val flow = ProgramFlow(args[0], dryRun)
    flow.go()
    println(flow.detected)
}
