package chucknorris

fun main() {
    operate()
}

fun operate() {
    while (true) {
        println("Please input operation (encode/decode/exit):")
        when (val operation = readln()) {
            "encode" -> encode()
            "decode" -> decode()
            "exit" -> {
                println("Bye!")
                return
            }

            else -> println("There is no '$operation' operation")
        }
    }
}

fun decode() {
    println("Input encoded string:")
    val input = readln().trim()
    val encodedStringBlocks = input.split(" ")
    val iter = encodedStringBlocks.listIterator()
    var binaryString = ""
    if (isValid(input)) {
        while (iter.hasNext()) {
            var item = iter.next()  // first block string "0" or "00"
            if(item == "0") {
                item = iter.next()        // second block of zeroes
                for (i in 1..item.length) binaryString += "1"   //creates a sequence of 1's if first block = "0"
            } else {
                item = iter.next()
                for (i in 1..item.length) binaryString += "0"  ////creates a sequence of 0's if first block = "00"
            }
        }
    } else {
        return
    }

    var charString = ""
    //if binary string length is multiple of 7
    try {
        while (binaryString != "") {
            charString += Integer.parseInt(binaryString.substring(0, 7), 2).toChar()
            binaryString = binaryString.substring(7, binaryString.length)
        }
        println("Decoded string:")
        println(charString)
    } catch (e: Exception) {
        println("Encoded string is not valid.")
    }
}

fun encode() {
    println("Input string:")

    val binaryString =
        readln().map { e -> e.code.toString(2).padStart(7, '0') }
            .joinToString(separator = "") { it }
    var result = ""
    var prevChar = '*'

    for (c in binaryString){
        if (c == prevChar) {
            result += "0"
        } else if (c == '1') {
            result += " 0 0"
        } else if (c == '0') {
            result += " 00 0"
        }
        prevChar = c
    }
    println("Encoded string:\n${result.substring(1, result.length)}")
}

fun isValid(input: String): Boolean {
    val list: List<String> = input.split(" ")
    if (input.toHashSet() != mutableSetOf(' ', '0') || list.size % 2 != 0) {
        println("Encoded string is not valid.")
        return false
    }
    for (i in list.indices step 2) {
        if (!(list[i] == "0" || list[i] == "00")) {
            println("Encoded string is not valid.")
            return false
        }
    }
    return true
}