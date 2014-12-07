.data
.text

.end main
.ent FUNC_fib
.globl FUNC_fib
FUNC_fib:
addi $sp, $sp, 28
sw $fp, -20($sp)
sw $ra, -24($sp)
li $t7, 0
move $t8, $t7
sw $t8, -16($sp)
li $t7, 0
move $t8, $t7
sw $t8, -12($sp)
li $t8, 0
lw $t7, -28($sp)
bne  $t7, $t8, LABEL_ELSE_BEGIN_0
li $v0, 0
lw $fp, -20($sp)
lw $ra, -24($sp)
addi $sp, $sp, -28
jr $ra
j LABEL_ELSE_END_0
LABEL_ELSE_BEGIN_0:
li $t8, 1
lw $t7, -28($sp)
bne  $t7, $t8, LABEL_ELSE_BEGIN_1
li $v0, 1
lw $fp, -20($sp)
lw $ra, -24($sp)
addi $sp, $sp, -28
jr $ra
j LABEL_ELSE_END_1
LABEL_ELSE_BEGIN_1:
li $t8, 1
lw $t7, -28($sp)
sub $t9, $t7, $t8
sw $t9, -8($sp)
addi $sp, $sp, 16
sw $t7, -12($sp)
sw $t8, -8($sp)
sw $t9, -4($sp)
lw $a0, -24($sp)
sw $a0, 0($sp)
jal FUNC_fib
lw $t7, -12($sp)
lw $t8, -8($sp)
lw $t9, -4($sp)
addi $sp, $sp, -16
sw $v0, -12($sp)
li $t8, 2
lw $t7, -28($sp)
sub $t9, $t7, $t8
sw $t9, -4($sp)
addi $sp, $sp, 16
sw $t7, -12($sp)
sw $t8, -8($sp)
sw $t9, -4($sp)
lw $a0, -20($sp)
sw $a0, 0($sp)
jal FUNC_fib
lw $t7, -12($sp)
lw $t8, -8($sp)
lw $t9, -4($sp)
addi $sp, $sp, -16
sw $v0, -16($sp)
lw $t8, -16($sp)
lw $t7, -12($sp)
add $t9, $t7, $t8
sw $t9, 0($sp)
lw $v0, 0($sp)
lw $fp, -20($sp)
lw $ra, -24($sp)
addi $sp, $sp, -28
jr $ra
LABEL_ELSE_END_1:
LABEL_ELSE_END_0:

.end FUNC_fib
.ent main
.globl main
main:
addi $sp, $sp, 12
sw $fp, -4($sp)
sw $ra, -8($sp)
li $t7, 0
move $t8, $t7
sw $t8, 0($sp)
addi $sp, $sp, 16
sw $t7, -12($sp)
sw $t8, -8($sp)
sw $t9, -4($sp)
li $a0, 7
sw $a0, 0($sp)
jal FUNC_fib
lw $t7, -12($sp)
lw $t8, -8($sp)
lw $t9, -4($sp)
addi $sp, $sp, -16
sw $v0, 0($sp)
li $v0, 1
lw $a0, 0($sp)
syscall
lw $fp, -4($sp)
lw $ra, -8($sp)
addi $sp, $sp, -12
jr $ra

