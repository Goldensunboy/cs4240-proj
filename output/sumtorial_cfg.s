.data
.text

.end main
.ent main
.globl main
main:
addi $sp, $sp, 32
sw $fp, -24($sp)
sw $ra, -28($sp)
lw $t0, -20($sp)
lw $t2, -16($sp)
lw $t1, -12($sp)
lw $t3, -8($sp)
li $t7, 0

move $t2, $t7

li $t7, 0

move $t3, $t7

li $t7, 10

move $t3, $t7


move $t1, $t3

li $t7, 1

move $t0, $t7

sw $t0, -20($sp)
sw $t2, -16($sp)
sw $t1, -12($sp)
sw $t3, -8($sp)

LABEL_FOR_START_0:
lw $t0, -20($sp)
lw $t1, -12($sp)
sw $t0, -20($sp)
sw $t1, -12($sp)
bgt  $t0, $t1, LABEL_FOR_END_0

lw $t0, -20($sp)
lw $t1, -16($sp)
lw $t2, -4($sp)
lw $t3, 0($sp)
add $t3, $t1, $t0

move $t1, $t3

li $t7, 1
add $t2, $t0, $t7

move $t0, $t2

sw $t0, -20($sp)
sw $t1, -16($sp)
sw $t2, -4($sp)
sw $t3, 0($sp)
j LABEL_FOR_START_0

LABEL_FOR_END_0:
li $v0, 1
lw $a0, -16($sp)

syscall
lw $fp, -24($sp)
lw $ra, -28($sp)
addi $sp, $sp, -32
jr $ra

