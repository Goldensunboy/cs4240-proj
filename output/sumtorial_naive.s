.data
.text

.end main
.ent main
.globl main
main:
addi $sp, $sp, 32
sw $fp, -24($sp)
sw $ra, -28($sp)
li $t7, 0

move $t8, $t7

sw $t8, -20($sp)
li $t7, 0

move $t8, $t7

sw $t8, -16($sp)
li $t7, 10

move $t8, $t7

sw $t8, -16($sp)
lw $t7, -16($sp)

move $t8, $t7

sw $t8, -12($sp)
li $t7, 1

move $t8, $t7

sw $t8, -8($sp)
LABEL_FOR_START_0:
lw $t7, -8($sp)
lw $t8, -12($sp)
bgt  $t7, $t8, LABEL_FOR_END_0
lw $t8, -8($sp)
lw $t7, -20($sp)
add $t9, $t7, $t8
sw $t9, -4($sp)
lw $t7, -4($sp)

move $t8, $t7

sw $t8, -20($sp)
li $t8, 1
lw $t7, -8($sp)
add $t9, $t7, $t8
sw $t9, 0($sp)
lw $t7, 0($sp)

move $t8, $t7

sw $t8, -8($sp)
j LABEL_FOR_START_0
LABEL_FOR_END_0:
li $v0, 1
lw $a0, -20($sp)

syscall
lw $fp, -24($sp)
lw $ra, -28($sp)
addi $sp, $sp, -32
jr $ra

