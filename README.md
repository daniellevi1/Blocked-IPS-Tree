Implmetation:
Employing binary tree data structure in order to store the CIDR blocked network address.
Given an IP, we determine if it's blocked by traveling down the binary tree to the leaves that are blocked.
complexity analysis
Time O(m * log n) = O(m * log 32)
Extra space: O(log n) (besides the binary tree)
 n = |ip_address_binary_length|, m=|CIDR ip's|
 
Traveling in the binary tree:
The binary tree leaves are blocked.
When searching IP in the tree we have 2 options:
First, it gets to a leaf, so it is in the range of blocked ip's. -> return false (blocked).
Second, while advancing in the binary IP represntation, if it doesn't have a son node representing the next bit in thr binary tree it means it is valid IP. -> return true (valid)
isAllowed method return true if the ip is not block, otherwise return false.
  



