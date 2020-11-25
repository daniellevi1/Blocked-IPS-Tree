Block suspicious IPs in CIDR notation, by represeting them as binery tree.
The binary tree leaves will be blocked.
When searching IP in the tree it has 2 options:
The first, if it gets to a leaf, it is in the range of blocked ip's. -> retur False
Second, while advancing in the binery IP represntation, if it doesn't have a son node representing the next bit in thr binery tree it means it is valid IP. -> return True

IpTree class:
createTree method
checkIpValidation method
