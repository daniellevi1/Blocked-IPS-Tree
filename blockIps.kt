public class TreeNode(
    var left1: TreeNode? = null,
    var right1: TreeNode? = null,
    var blocked: Boolean? = false
    )

class IpTree(private val root: TreeNode){

    // Employing a binary tree data structure in order to store the CIDR blocked network address
    // Time complexity: O(log(n))
    public fun createTree(ip: String, node:TreeNode){
        var currNode = node;
        for(bit in ip){
            if (bit == '0'){
                if (currNode.right1 != null) {
                    currNode = currNode.right1!!
                }
                else{
                    val newNode = TreeNode()
                    currNode.right1 = newNode
                    currNode = newNode
                }
            }
            if (bit == '1'){
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

    // Given an IP, we determine if it's blocked by traveling down the binary tree.
    // Time complexity: O(log(n)), n = |ip_address_binary_length|
    public fun validationIps(curr_ip: String):Boolean{
        var currNode = this.root
        for (i in 0..curr_ip.length){
            val bit = curr_ip[i]
            if(currNode.right1 != null){
                if(currNode.right1!!.blocked!!){ return false }
            }
            if(currNode.left1 != null){
                if(currNode.left1!!.blocked!!){ return false }
            }

            if (bit == '0'){
                if (currNode.right1 != null) currNode = currNode.right1!!
                else { return true }
            }
            else if(bit == '1') {
                if (currNode.left1 != null) currNode = currNode.left1!!
                else { return true}
            }
        }
        return true
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

public fun genericIsAllowed(incomingIpsArray: Array<String>, cidrIpsToBlock: Array<String>){
    var arrayOfCidrIp =  cidrIpsToBlock
    val filterdIps = arrayOfNulls<String>(arrayOfCidrIp.size)
    val root = TreeNode()
    val ipTree = IpTree(root)

    var i = 0
    for (cidr in arrayOfCidrIp) {
        filterdIps[i] = createIpFromCidr(cidr)
        i += 1
    }

    for (ip_idx in 0 until filterdIps.size){
        val cidrIp = filterdIps[ip_idx]
        if (cidrIp != null) ipTree.createTree(cidrIp, root)
    }
    for(incomingIp in incomingIpsArray) {
        val test = convertIpToBin(incomingIp)
        val result = ipTree.validationIps(test)
        if (result) print("Ip is valid\n") else print("Ip is not valid\n")
    }
}

public fun isAllowed(incomingIp: String): Boolean{
    val arrayOfCidrIp = arrayOf("192.3.4.92/8", "192.3.77.92/16", "192.3.32.92/24")
    val filterdIps = arrayOfNulls<String>(arrayOfCidrIp.size)
    val root = TreeNode()
    val ipTree = IpTree(root)

    var i = 0
    for (cidr in arrayOfCidrIp) {
        filterdIps[i] = createIpFromCidr(cidr)
        i += 1
    }

    for (ip_idx in 0 until filterdIps.size){
        val cidrIp = filterdIps[ip_idx]
        if (cidrIp != null) {
            ipTree.createTree(cidrIp, root)
        }
    }

    val test = convertIpToBin(incomingIp)
    val result = ipTree.validationIps(test)
    return if (result) {
        print("Ip is valid\n")
        true
    } else {
        print("Ip is not valid\n")
        false
    }
}


fun main(args: Array<String>) {
//    print(isAllowed("192.40.30.3"))
//    print(isAllowed("191.3.4.0"))
    val cidrIpsArray = arrayOf("192.3.4.92/31", "192.3.76.92/24", "192.3.32.92/24", "35.3.32.92/8")
    val incomingIpsArray = arrayOf("192.40.30.3", "191.40.30.3", "35.6.222.121", "192.3.87.92")
    genericIsAllowed(incomingIpsArray, cidrIpsArray)
}

