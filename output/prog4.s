.data
.text

.end main
.ent FUNC_fib
.globl FUNC_fib
FUNC_fib:
addi $sp, $sp, 28
sw $fp, -20($sp)
sw $ra, -24($sp)
lw $t0, -16($sp)
lw $t1, -12($sp)
lw $t2, -8($sp)
lw $t3, -4($sp)
lw $t4, -28($sp)
lw $t5, 0($sp)
li $t7, 0
move $t2, $t7
li $t7, 0
move $t5, $t7
li $t7, 0
bne  $t4, $t7, LABEL_ELSE_BEGIN_0

sw $t0, -16($sp)
sw $t1, -12($sp)
sw $t2, -8($sp)
sw $t3, -4($sp)
sw $t4, -28($sp)
sw $t5, 0($sp)
li $v0, 0
lw $fp, -20($sp)
lw $ra, -24($sp)
addi $sp, $sp, -28
jr $ra
sw $t0, -16($sp)
sw $t1, -12($sp)
sw $t2, -8($sp)
sw $t3, -4($sp)
sw $t4, -28($sp)
sw $t5, 0($sp)
j LABEL_ELSE_END_0

LABEL_ELSE_BEGIN_0:
li $t7, 1
bne  $t4, $t7, LABEL_ELSE_BEGIN_1

sw $t0, -16($sp)
sw $t1, -12($sp)
sw $t2, -8($sp)
sw $t3, -4($sp)
sw $t4, -28($sp)
sw $t5, 0($sp)
li $v0, 1
lw $fp, -20($sp)
lw $ra, -24($sp)
addi $sp, $sp, -28
jr $ra
sw $t0, -16($sp)
sw $t1, -12($sp)
sw $t2, -8($sp)
sw $t3, -4($sp)
sw $t4, -28($sp)
sw $t5, 0($sp)
j LABEL_ELSE_END_1

LABEL_ELSE_BEGIN_1:
li $t7, 1
sub $t0, $t4, $t7
sw $t0, -16($sp)
sw $t1, -12($sp)
sw $t2, -8($sp)
sw $t3, -4($sp)
sw $t4, -28($sp)
sw $t5, 0($sp)
addi $sp, $sp, 32
sw $t0, -28($sp)
sw $t1, -24($sp)
sw $t2, -20($sp)
sw $t3, -16($sp)
sw $t4, -12($sp)
sw $t5, -8($sp)
sw $t7, -4($sp)

lw $a0, -48($sp)

sw $a0, 0($sp)
jal FUNC_fib
lw $t0, -28($sp)
lw $t1, -24($sp)
lw $t2, -20($sp)
lw $t3, -16($sp)
lw $t4, -12($sp)
lw $t5, -8($sp)
lw $t7, -4($sp)
addi $sp, $sp, -32
sw $v0, 0($sp)
li $t7, 2
sub $t3, $t4, $t7
sw $t0, -16($sp)
sw $t1, -12($sp)
sw $t2, -8($sp)
sw $t3, -4($sp)
sw $t4, -28($sp)
sw $t5, 0($sp)
addi $sp, $sp, 32
sw $t0, -28($sp)
sw $t1, -24($sp)
sw $t2, -20($sp)
sw $t3, -16($sp)
sw $t4, -12($sp)
sw $t5, -8($sp)
sw $t7, -4($sp)

lw $a0, -36($sp)

sw $a0, 0($sp)
jal FUNC_fib
lw $t0, -28($sp)
lw $t1, -24($sp)
lw $t2, -20($sp)
lw $t3, -16($sp)
lw $t4, -12($sp)
lw $t5, -8($sp)
lw $t7, -4($sp)
addi $sp, $sp, -32
sw $v0, -8($sp)
add $t1, $t5, $t2
sw $t0, -16($sp)
sw $t1, -12($sp)
sw $t2, -8($sp)
sw $t3, -4($sp)
sw $t4, -28($sp)
sw $t5, 0($sp)
lw $v0, -12($sp)
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
lw $t0, 0($sp)
li $t7, 0
move $t0, $t7
sw $t0, 0($sp)
addi $sp, $sp, 32
sw $t0, -28($sp)
sw $t1, -24($sp)
sw $t2, -20($sp)
sw $t3, -16($sp)
sw $t4, -12($sp)
sw $t5, -8($sp)
sw $t7, -4($sp)

li $a0, 7
sw $a0, 0($sp)
jal FUNC_fib
lw $t0, -28($sp)
lw $t1, -24($sp)
lw $t2, -20($sp)
lw $t3, -16($sp)
lw $t4, -12($sp)
lw $t5, -8($sp)
lw $t7, -4($sp)
addi $sp, $sp, -32
sw $v0, 0($sp)
sw $t0, 0($sp)
li $v0, 1
lw $a0, 0($sp)

syscall
sw $t0, 0($sp)
lw $fp, -4($sp)
lw $ra, -8($sp)
addi $sp, $sp, -12
jr $ra

