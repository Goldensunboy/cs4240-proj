.data
.text

.end main
.ent main
.globl main
main:
addi $sp, $sp, 40
sw $fp, -32($sp)
sw $ra, -36($sp)
lw $t0, -28($sp)
lw $t1, -24($sp)
lw $t2, -20($sp)
lw $t4, -16($sp)
lw $t3, -12($sp)
lw $t5, -8($sp)
li $t7, 0

move $t2, $t7

li $t7, 0

move $t5, $t7

li $t7, 0

move $t3, $t7

li $t7, 0

move $t1, $t7

li $t7, 0

move $t4, $t7

li $t7, 1

move $t1, $t7

li $t7, 6

move $t2, $t7

add $t0, $t4, $t1

move $t3, $t0

sw $t0, -28($sp)
sw $t1, -24($sp)
sw $t2, -20($sp)
sw $t4, -16($sp)
sw $t3, -12($sp)
sw $t5, -8($sp)

LABEL_WHILE_TOP_0:
lw $t0, -24($sp)
lw $t1, -4($sp)
lw $t2, -20($sp)
lw $t5, -16($sp)
lw $t4, 0($sp)
lw $t3, -12($sp)
lw $t6, -8($sp)
li $t7, 0
ble  $t2, $t7, LABEL_WHILE_END_0


move $t6, $t3

add $t4, $t3, $t5

move $t3, $t4


move $t0, $t5


move $t5, $t6

li $t7, 1
sub $t1, $t2, $t7

move $t2, $t1

sw $t0, -24($sp)
sw $t1, -4($sp)
sw $t2, -20($sp)
sw $t5, -16($sp)
sw $t4, 0($sp)
sw $t3, -12($sp)
sw $t6, -8($sp)
j LABEL_WHILE_TOP_0

LABEL_WHILE_END_0:
sw $t0, -24($sp)
sw $t1, -4($sp)
sw $t2, -20($sp)
sw $t5, -16($sp)
sw $t4, 0($sp)
sw $t3, -12($sp)
sw $t6, -8($sp)
li $v0, 1
lw $a0, -12($sp)

syscall
sw $t0, -24($sp)
sw $t1, -4($sp)
sw $t2, -20($sp)
sw $t5, -16($sp)
sw $t4, 0($sp)
sw $t3, -12($sp)
sw $t6, -8($sp)
lw $fp, -32($sp)
lw $ra, -36($sp)
addi $sp, $sp, -40
jr $ra

