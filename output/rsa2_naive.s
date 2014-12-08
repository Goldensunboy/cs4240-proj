.data
.text

.end main
.ent FUNC_mod
.globl FUNC_mod
FUNC_mod:
addi $sp, $sp, 16
sw $fp, -8($sp)
sw $ra, -12($sp)
li $t7, 0

move $t8, $t7

sw $t8, -4($sp)
lw $t8, -4($sp)
sub $t9, $t8, $t8
sw $t9, 0($sp)
lw $t7, 0($sp)

move $t8, $t7

sw $t8, -4($sp)
lw $v0, -4($sp)
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
li $t7, 3

move $t8, $t7

sw $t8, -12($sp)
li $t7, 4

move $t8, $t7

sw $t8, -8($sp)
li $t7, 4

move $t8, $t7

sw $t8, -4($sp)
lw $t8, -8($sp)
lw $t7, -4($sp)
mul $t9, $t7, $t8
sw $t9, 0($sp)
addi $sp, $sp, 24
sw $t7, -20($sp)
sw $t8, -16($sp)
sw $t9, -12($sp)

li $a0, 3
sw $a0, -8($sp)

lw $a0, -24($sp)

sw $a0, -4($sp)

li.s $f12, 4.0

swc1 $f12, 0($sp)
jal FUNC_mod
lw $t7, -20($sp)
lw $t8, -16($sp)
lw $t9, -12($sp)
addi $sp, $sp, -24
sw $v0, -12($sp)
lw $t7, -12($sp)
li $t8, 9
lw $t7, -12($sp)
bne  $t7, $t8, LABEL_ELSE_BEGIN_0
li $t7, 10

move $t8, $t7

sw $t8, -12($sp)
j LABEL_ELSE_END_0
LABEL_ELSE_BEGIN_0:
LABEL_ELSE_END_0:
lw $fp, -16($sp)
lw $ra, -20($sp)
addi $sp, $sp, -24
jr $ra

