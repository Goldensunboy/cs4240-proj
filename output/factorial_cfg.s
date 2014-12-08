.data
.text

.end main
.ent main
.globl main
main:
addi $sp, $sp, 28
sw $fp, -20($sp)
sw $ra, -24($sp)
lw $t0, -16($sp)
lw $t1, -12($sp)
lw $t2, -8($sp)
li $t7, 0

move $t0, $t7

li $t7, 0

move $t2, $t7

li $t7, 0

move $t1, $t7

li $t7, 1

move $t2, $t7

li $t7, 1

move $t0, $t7

li $t7, 5

move $t1, $t7

sw $t0, -16($sp)
sw $t1, -12($sp)
sw $t2, -8($sp)

LABEL_WHILE_TOP_0:
li $t8, 0
bne  $t8, $t8, LABEL_WHILE_END_0

lw $t0, -12($sp)
lw $t1, -8($sp)
sw $t0, -12($sp)
sw $t1, -8($sp)
ble  $t1, $t0, LABEL_ELSE_BEGIN_0

j LABEL_WHILE_END_0

j LABEL_ELSE_END_0

LABEL_ELSE_BEGIN_0:

LABEL_ELSE_END_0:
lw $t0, -16($sp)
lw $t1, -4($sp)
lw $t3, -8($sp)
lw $t2, 0($sp)
mul $t1, $t0, $t3

move $t0, $t1

li $t7, 1
add $t2, $t3, $t7

move $t3, $t2

sw $t0, -16($sp)
sw $t1, -4($sp)
sw $t3, -8($sp)
sw $t2, 0($sp)
j LABEL_WHILE_TOP_0

LABEL_WHILE_END_0:
li $v0, 1
lw $a0, -16($sp)

syscall
lw $fp, -20($sp)
lw $ra, -24($sp)
addi $sp, $sp, -28
jr $ra

