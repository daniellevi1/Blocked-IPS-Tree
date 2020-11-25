class TreeNode(
    var left1: TreeNode? = null,
    var right1: TreeNode? = null,
    var blocked: Boolean? = false
    )

class IpTree(private val root: TreeNode){

    fun createTree(ip: String, node:TreeNode){
        var currNode = node;
        for(element in ip){
            if (element == '0'){
                if (currNode.right1 != null) {
                    currNode = currNode.right1!!
                }
                else{
                    val newNode = TreeNode()
                    currNode.right1 = newNode
                    currNode = newNode
                }
            }
            if (element == '1'){
                if(currNode.left1 != null){
                    currNode = currNode.left1!!
                }
                else{
                    val newNode = TreeNode()
                    currNode.left1 = newNode
                    currNode = newNode
                }
            }
        }
        currNode.blocked = true
    }

    fun checkIpValidation(curr_ip: String):Boolean{
        var currNode = this.root
        for (i in 0..curr_ip.length){
            val bit = curr_ip[i]
            if(currNode.right1 != null){
                if(currNode.right1!!.blocked!!){ return true }
            }
            if(currNode.left1 != null){
                if(currNode.left1!!.blocked!!){ return true }
            }

            if (bit == '0'){
                if(currNode.right1 == null){ return false }
                else{
                    currNode = currNode.right1!!
                }
            }
            else if(bit == '1') {
                if (currNode.left1 == null) { return false}
                else {
                    currNode = currNode.left1!!
                }
            }
        }
        return false
    }
}


fun getBinaryNumber(decimalNumber: Int): String {
    var decimalNumber = decimalNumber
    val binaryStr = StringBuilder()

    while (decimalNumber > 0) {
        val r = decimalNumber % 2
        decimalNumber /= 2
        binaryStr.append(r)
    }

    return binaryStr.reverse().toString()
}

fun convertIpToBin(ip: String): String {
    val ipList = ip.split(".").toTypedArray()
    var filteredIp = ""

    for (i in 0 until ipList.size){
        val x = ipList[i]
        var bNum = getBinaryNumber(x.toInt())
        val padding = "0".repeat(8 - bNum.length)
        bNum = padding + bNum
        filteredIp += bNum
    }
    return filteredIp
}

fun createIpFromCidr(cidr_ip: String): String{
    val idx = cidr_ip.indexOf('/')
    val ip = cidr_ip.substring(0, idx)
    var binIp = convertIpToBin(ip)
    val counter = (cidr_ip.substring(idx+1)).toInt()
    binIp = binIp.substring(0, counter)

    return binIp
}


fun isAllowed(incomingIp: String): Boolean{
    val arrayOfCidrIp = arrayOf("192.3.4.92/8", "192.3.77.92/16", "192.3.32.92/24")
    val map = arrayOfNulls<String>(arrayOfCidrIp.size)
    val root = TreeNode()
    val ipTree = IpTree(root)

    var i = 0
    for (element in arrayOfCidrIp) {
        map[i] = createIpFromCidr(element)
        i += 1
    }

    for (ip_idx in 0 until map.size){
        val cidrIp = map[ip_idx]
        if (cidrIp != null) {
            ipTree.createTree(cidrIp, root)
        }
    }

    val test = convertIpToBin(incomingIp)
    val result = ipTree.checkIpValidation(test)
    return if (result) {
        print("Ip is not valid\n")
        false
    } else {
        print("Ip is valid\n")
        true
    }
}



fun main(args: Array<String>) {
    print(isAllowed("192.40.30.3"))
//    print(isAllowed("191.3.4.0"))
}

