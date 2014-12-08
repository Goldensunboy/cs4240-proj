.data
.text

.end main
.ent FUNC_mod
.globl FUNC_mod
FUNC_mod:
addi $sp, $sp, 16
sw $fp, -8($sp)
sw $ra, -12($sp)
lw $t0, -4($sp)
lw $t1, 0($sp)
li $t7, 0

move $t1, $t7

sub $t0, $t1, $t1

move $t1, $t0

sw $t0, -4($sp)
sw $t1, 0($sp)
lw $v0, 0($sp)
lw $fp, -8($sp)
lw $ra, -12($sp)
addi $sp, $sp, -16
jr $ra


.end FUNC_mod
.ent main
.globl main
main:
addi $sp, $sp, 24
sw $fp, -16($sp)
sw $ra, -20($sp)
lw $t0, -12($sp)
lw $t1, -8($sp)
lw $t3, -4($sp)
lw $t2, 0($sp)
li $t7, 3

move $t0, $t7

li $t7, 4

move $t1, $t7

li $t7, 4

move $t3, $t7

mul $t2, $t3, $t1
sw $t0, -12($sp)
sw $t1, -8($sp)
sw $t3, -4($sp)
sw $t2, 0($sp)
addi $sp, $sp, 24
sw $t0, -20($sp)
sw $t1, -16($sp)
sw $t7, -12($sp)

li $a0, 3
sw $a0, -8($sp)

lw $a0, -24($sp)

sw $a0, -4($sp)

li.s $f12, 4.0

swc1 $f12, 0($sp)
jal FUNC_mod
lw $t0, -20($sp)
lw $t1, -16($sp)
lw $t7, -12($sp)
addi $sp, $sp, -24
sw $v0, -12($sp)
lw $t0, -12($sp)
li $t7, 9
sw $t0, -12($sp)
sw $t1, -8($sp)
sw $t3, -4($sp)
sw $t2, 0($sp)
bne  $t0, $t7, LABEL_ELSE_BEGIN_0

lw $t0, -12($sp)
li $t7, 10

move $t0, $t7

sw $t0, -12($sp)
j LABEL_ELSE_END_0

LABEL_ELSE_BEGIN_0:

LABEL_ELSE_END_0:
lw $fp, -16($sp)
lw $ra, -20($sp)
addi $sp, $sp, -24
jr $ra

