package com.github.atamanroman.deob

import java.io.File


fun main(args: Array<String>) {


    val dryRun = args.contains("-n")
    val argList = args.toList().minus("-n")


    if(argList.isEmpty()) {
        println(help)
        return
    }

    if(!File(argList[0]).isDirectory) {
        println("Target ${argList[0]} does not exist. Nothing to do.")
        return
    }

    val flow = ProgramFlow(args[0], dryRun)
    flow.go()
    println(flow.detected)
}

val help = """Root path must be provided.
E.g.: `./deob /my/sample/folder`

Available options:
    -n (dry run)
"""