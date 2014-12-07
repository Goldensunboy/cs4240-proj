.data
.text
.ent FUNC_mod
.globl FUNC_mod
FUNC_mod:
addi $sp, $sp, 16
sw $fp, -8($sp)
sw $ra, -12($sp)
li $t7, 0
move $t8, $t7
sw $t8, -4($sp)
LABEL_WHILE_TOP_0:
lw $t7, -20($sp)
lw $t8, -16($sp)
blt  $t7, $t8, LABEL_WHILE_END_0
lw $t7, -20($sp)
lw $t8, -16($sp)
sub $t9, $t7, $t8
sw $t9, 0($sp)
lw $t7, 0($sp)
move $t8, $t7
sw $t8, -20($sp)
j LABEL_WHILE_TOP_0
LABEL_WHILE_END_0:
lw $v0, -20($sp)
lw $fp, -8($sp)
lw $ra, -12($sp)
addi $sp, $sp, -16
jr $ra

.end FUNC_mod
.ent FUNC_pow
.globl FUNC_pow
FUNC_pow:
addi $sp, $sp, 20
sw $fp, -12($sp)
sw $ra, -16($sp)
li $t7, 0
move $t8, $t7
sw $t8, -8($sp)
li $t8, 0
lw $t7, -20($sp)
bne  $t7, $t8, LABEL_ELSE_BEGIN_0
li $v0, 1
lw $fp, -12($sp)
lw $ra, -16($sp)
addi $sp, $sp, -20
jr $ra
j LABEL_ELSE_END_0
LABEL_ELSE_BEGIN_0:
li $t8, 1
lw $t7, -20($sp)
sub $t9, $t7, $t8
sw $t9, -4($sp)
addi $sp, $sp, 20
sw $t7, -16($sp)
sw $t8, -12($sp)
sw $t9, -8($sp)

lw $a0, -44($sp)

sw $a0, -4($sp)

lw $a0, -24($sp)

sw $a0, 0($sp)
jal FUNC_pow
lw $t7, -16($sp)
lw $t8, -12($sp)
lw $t9, -8($sp)
addi $sp, $sp, -20
sw $v0, -8($sp)
lw $t7, -8($sp)
lw $t8, -8($sp)
lw $t7, -24($sp)
mul $t9, $t7, $t8
sw $t9, 0($sp)
lw $v0, 0($sp)
lw $fp, -12($sp)
lw $ra, -16($sp)
addi $sp, $sp, -20
jr $ra
LABEL_ELSE_END_0:

.end FUNC_pow
.ent FUNC_modPow
.globl FUNC_modPow
FUNC_modPow:
addi $sp, $sp, 20
sw $fp, -12($sp)
sw $ra, -16($sp)
li $t7, 0
move $t8, $t7
sw $t8, -8($sp)
li $t8, 0
lw $t7, -24($sp)
bne  $t7, $t8, LABEL_ELSE_BEGIN_1
li $v0, 1
lw $fp, -12($sp)
lw $ra, -16($sp)
addi $sp, $sp, -20
jr $ra
j LABEL_ELSE_END_1
LABEL_ELSE_BEGIN_1:
li $t8, 1
lw $t7, -24($sp)
sub $t9, $t7, $t8
sw $t9, -4($sp)
addi $sp, $sp, 24
sw $t7, -20($sp)
sw $t8, -16($sp)
sw $t9, -12($sp)

lw $a0, -52($sp)

sw $a0, -8($sp)

lw $a0, -28($sp)

sw $a0, -4($sp)

lw $a0, -44($sp)

sw $a0, 0($sp)
jal FUNC_modPow
lw $t7, -20($sp)
lw $t8, -16($sp)
lw $t9, -12($sp)
addi $sp, $sp, -24
sw $v0, -8($sp)
lw $t7, -8($sp)
lw $t7, -28($sp)
lw $t8, -8($sp)
mul $t9, $t7, $t8
sw $t9, 0($sp)
lw $t7, 0($sp)
move $t8, $t7
sw $t8, -8($sp)
addi $sp, $sp, 20
sw $t7, -16($sp)
sw $t8, -12($sp)
sw $t9, -8($sp)

lw $a0, -28($sp)

sw $a0, -4($sp)

lw $a0, -40($sp)

sw $a0, 0($sp)
jal FUNC_mod
lw $t7, -16($sp)
lw $t8, -12($sp)
lw $t9, -8($sp)
addi $sp, $sp, -20
sw $v0, -8($sp)
lw $t7, -8($sp)
lw $v0, -8($sp)
lw $fp, -12($sp)
lw $ra, -16($sp)
addi $sp, $sp, -20
jr $ra
LABEL_ELSE_END_1:

.end FUNC_modPow
.ent FUNC_gcd
.globl FUNC_gcd
FUNC_gcd:
addi $sp, $sp, 20
sw $fp, -12($sp)
sw $ra, -16($sp)
li $t7, 0
move $t8, $t7
sw $t8, -8($sp)
lw $t7, -24($sp)
lw $t8, -20($sp)
bne  $t7, $t8, LABEL_ELSE_BEGIN_2
lw $v0, -24($sp)
lw $fp, -12($sp)
lw $ra, -16($sp)
addi $sp, $sp, -20
jr $ra
j LABEL_ELSE_END_2
LABEL_ELSE_BEGIN_2:
lw $t7, -24($sp)
lw $t8, -20($sp)
ble  $t7, $t8, LABEL_ELSE_BEGIN_3
lw $t7, -24($sp)
lw $t8, -20($sp)
sub $t9, $t7, $t8
sw $t9, -4($sp)
addi $sp, $sp, 20
sw $t7, -16($sp)
sw $t8, -12($sp)
sw $t9, -8($sp)

lw $a0, -24($sp)

sw $a0, -4($sp)

lw $a0, -40($sp)

sw $a0, 0($sp)
jal FUNC_gcd
lw $t7, -16($sp)
lw $t8, -12($sp)
lw $t9, -8($sp)
addi $sp, $sp, -20
sw $v0, -8($sp)
lw $t7, -8($sp)
j LABEL_ELSE_END_3
LABEL_ELSE_BEGIN_3:
lw $t8, -24($sp)
lw $t7, -20($sp)
sub $t9, $t7, $t8
sw $t9, 0($sp)
addi $sp, $sp, 20
sw $t7, -16($sp)
sw $t8, -12($sp)
sw $t9, -8($sp)

lw $a0, -44($sp)

sw $a0, -4($sp)

lw $a0, -20($sp)

sw $a0, 0($sp)
jal FUNC_gcd
lw $t7, -16($sp)
lw $t8, -12($sp)
lw $t9, -8($sp)
addi $sp, $sp, -20
sw $v0, -8($sp)
lw $t7, -8($sp)
LABEL_ELSE_END_3:
lw $v0, -8($sp)
lw $fp, -12($sp)
lw $ra, -16($sp)
addi $sp, $sp, -20
jr $ra
LABEL_ELSE_END_2:
li $v0, 0
lw $fp, -12($sp)
lw $ra, -16($sp)
addi $sp, $sp, -20
jr $ra

.end FUNC_gcd
.ent FUNC_modularInverse
.globl FUNC_modularInverse
FUNC_modularInverse:
addi $sp, $sp, 24
sw $fp, -16($sp)
sw $ra, -20($sp)
li $t7, 1
move $t8, $t7
sw $t8, -12($sp)
li $t7, 1
move $t8, $t7
sw $t8, -8($sp)
LABEL_WHILE_TOP_1:
lw $t7, -8($sp)
li $t8, 1000
bge  $t7, $t8, LABEL_WHILE_END_1
lw $t7, -28($sp)
lw $t8, -8($sp)
mul $t9, $t7, $t8
sw $t9, -4($sp)
addi $sp, $sp, 20
sw $t7, -16($sp)
sw $t8, -12($sp)
sw $t9, -8($sp)

lw $a0, -24($sp)

sw $a0, -4($sp)

lw $a0, -44($sp)

sw $a0, 0($sp)
jal FUNC_mod
lw $t7, -16($sp)
lw $t8, -12($sp)
lw $t9, -8($sp)
addi $sp, $sp, -20
sw $v0, -12($sp)
lw $t7, -12($sp)
li $t8, 1
lw $t7, -12($sp)
bne  $t7, $t8, LABEL_ELSE_BEGIN_4
lw $v0, -8($sp)
lw $fp, -16($sp)
lw $ra, -20($sp)
addi $sp, $sp, -24
jr $ra
j LABEL_ELSE_END_4
LABEL_ELSE_BEGIN_4:
li $t8, 1
lw $t7, -8($sp)
add $t9, $t7, $t8
sw $t9, 0($sp)
lw $t7, 0($sp)
move $t8, $t7
sw $t8, -8($sp)
LABEL_ELSE_END_4:
j LABEL_WHILE_TOP_1
LABEL_WHILE_END_1:
li $v0, 0
lw $fp, -16($sp)
lw $ra, -20($sp)
addi $sp, $sp, -24
jr $ra

.end FUNC_modularInverse
.ent FUNC_genkey
.globl FUNC_genkey
FUNC_genkey:
addi $sp, $sp, 44
sw $fp, -36($sp)
sw $ra, -40($sp)
li $t7, 0
move $t8, $t7
sw $t8, -32($sp)
li $t7, 0
move $t8, $t7
sw $t8, -28($sp)
li $t7, 0
move $t8, $t7
sw $t8, -24($sp)
li $t7, 0
move $t8, $t7
sw $t8, -20($sp)
lw $t7, -52($sp)
li $t8, 1
sub $t9, $t7, $t8
sw $t9, -16($sp)
lw $t7, -48($sp)
li $t8, 1
sub $t9, $t7, $t8
sw $t9, -12($sp)
lw $t8, -12($sp)
lw $t7, -16($sp)
mul $t9, $t7, $t8
sw $t9, -8($sp)
lw $t7, -8($sp)
move $t8, $t7
sw $t8, -20($sp)
li $t8, 1
lw $t7, -20($sp)
sub $t9, $t7, $t8
sw $t9, -4($sp)
lw $t7, -4($sp)
move $t8, $t7
sw $t8, -24($sp)
LABEL_WHILE_TOP_2:
li $t8, 1
lw $t7, -24($sp)
ble  $t7, $t8, LABEL_WHILE_END_2
addi $sp, $sp, 20
sw $t7, -16($sp)
sw $t8, -12($sp)
sw $t9, -8($sp)

lw $a0, -44($sp)

sw $a0, -4($sp)

lw $a0, -40($sp)

sw $a0, 0($sp)
jal FUNC_gcd
lw $t7, -16($sp)
lw $t8, -12($sp)
lw $t9, -8($sp)
addi $sp, $sp, -20
sw $v0, -32($sp)
lw $t7, -32($sp)
li $t8, 1
lw $t7, -32($sp)
bne  $t7, $t8, LABEL_ELSE_BEGIN_5
j LABEL_WHILE_END_2
j LABEL_ELSE_END_5
LABEL_ELSE_BEGIN_5:
li $t8, 1
lw $t7, -24($sp)
sub $t9, $t7, $t8
sw $t9, 0($sp)
lw $t7, 0($sp)
move $t8, $t7
sw $t8, -24($sp)
LABEL_ELSE_END_5:
j LABEL_WHILE_TOP_2
LABEL_WHILE_END_2:
li $t8, 1
lw $t7, -24($sp)
bne  $t7, $t8, LABEL_ELSE_BEGIN_6
li $v0, 0
lw $fp, -36($sp)
lw $ra, -40($sp)
addi $sp, $sp, -44
jr $ra
j LABEL_ELSE_END_6
LABEL_ELSE_BEGIN_6:
li $t8, 0
lw $t7, -44($sp)
bne  $t7, $t8, LABEL_ELSE_BEGIN_7
lw $v0, -24($sp)
lw $fp, -36($sp)
lw $ra, -40($sp)
addi $sp, $sp, -44
jr $ra
j LABEL_ELSE_END_7
LABEL_ELSE_BEGIN_7:
LABEL_ELSE_END_7:
LABEL_ELSE_END_6:
addi $sp, $sp, 20
sw $t7, -16($sp)
sw $t8, -12($sp)
sw $t9, -8($sp)

lw $a0, -44($sp)

sw $a0, -4($sp)

lw $a0, -40($sp)

sw $a0, 0($sp)
jal FUNC_modularInverse
lw $t7, -16($sp)
lw $t8, -12($sp)
lw $t9, -8($sp)
addi $sp, $sp, -20
sw $v0, -28($sp)
lw $t7, -28($sp)
lw $v0, -28($sp)
lw $fp, -36($sp)
lw $ra, -40($sp)
addi $sp, $sp, -44
jr $ra

.end FUNC_genkey
.ent main
.globl main
main:
addi $sp, $sp, 72
sw $fp, -64($sp)
sw $ra, -68($sp)
li $t7, 0
move $t8, $t7
sw $t8, -60($sp)
li $t7, 0
move $t8, $t7
sw $t8, -56($sp)
li $t7, 0
move $t8, $t7
sw $t8, -52($sp)
li $t7, 0
move $t8, $t7
sw $t8, -48($sp)
li $t7, 0
move $t8, $t7
sw $t8, -44($sp)
li $t7, 0
move $t8, $t7
sw $t8, -40($sp)
li $t7, 0
move $t8, $t7
sw $t8, -36($sp)
li $t7, 0
move $t8, $t7
sw $t8, -32($sp)
li $t7, 0
move $t8, $t7
sw $t8, -28($sp)
li $t7, 3
move $t8, $t7
sw $t8, -24($sp)
li $t7, 11
move $t8, $t7
sw $t8, -20($sp)
li $t7, 5
move $t8, $t7
sw $t8, -16($sp)
li $t7, 7
move $t8, $t7
sw $t8, -12($sp)
li $t7, 0
mtc1 $t7, $f30
cvt.s.w $f30, $f30
mov.s $f29, $f30
swc1 $f29, -8($sp)
lw $t7, -24($sp)
lw $t8, -20($sp)
mul $t9, $t7, $t8
sw $t9, -4($sp)
lw $t7, -4($sp)
move $t8, $t7
sw $t8, -44($sp)
lw $t7, -16($sp)
lw $t8, -12($sp)
mul $t9, $t7, $t8
sw $t9, 0($sp)
lw $t7, 0($sp)
move $t8, $t7
sw $t8, -48($sp)
addi $sp, $sp, 24
sw $t7, -20($sp)
sw $t8, -16($sp)
sw $t9, -12($sp)

lw $a0, -48($sp)

sw $a0, -8($sp)

lw $a0, -44($sp)

sw $a0, -4($sp)

li $a0, 0
sw $a0, 0($sp)
jal FUNC_genkey
lw $t7, -20($sp)
lw $t8, -16($sp)
lw $t9, -12($sp)
addi $sp, $sp, -24
sw $v0, -28($sp)
lw $t7, -28($sp)
addi $sp, $sp, 24
sw $t7, -20($sp)
sw $t8, -16($sp)
sw $t9, -12($sp)

lw $a0, -48($sp)

sw $a0, -8($sp)

lw $a0, -44($sp)

sw $a0, -4($sp)

li $a0, 1
sw $a0, 0($sp)
jal FUNC_genkey
lw $t7, -20($sp)
lw $t8, -16($sp)
lw $t9, -12($sp)
addi $sp, $sp, -24
sw $v0, -36($sp)
lw $t7, -36($sp)
addi $sp, $sp, 24
sw $t7, -20($sp)
sw $t8, -16($sp)
sw $t9, -12($sp)

lw $a0, -40($sp)

sw $a0, -8($sp)

lw $a0, -36($sp)

sw $a0, -4($sp)

li $a0, 0
sw $a0, 0($sp)
jal FUNC_genkey
lw $t7, -20($sp)
lw $t8, -16($sp)
lw $t9, -12($sp)
addi $sp, $sp, -24
sw $v0, -32($sp)
lw $t7, -32($sp)
addi $sp, $sp, 24
sw $t7, -20($sp)
sw $t8, -16($sp)
sw $t9, -12($sp)

lw $a0, -40($sp)

sw $a0, -8($sp)

lw $a0, -36($sp)

sw $a0, -4($sp)

li $a0, 1
sw $a0, 0($sp)
jal FUNC_genkey
lw $t7, -20($sp)
lw $t8, -16($sp)
lw $t9, -12($sp)
addi $sp, $sp, -24
sw $v0, -40($sp)
lw $t7, -40($sp)
li $t7, 45
move $t8, $t7
sw $t8, -52($sp)
addi $sp, $sp, 24
sw $t7, -20($sp)
sw $t8, -16($sp)
sw $t9, -12($sp)

lw $a0, -76($sp)

sw $a0, -8($sp)

lw $a0, -56($sp)

sw $a0, -4($sp)

lw $a0, -72($sp)

sw $a0, 0($sp)
jal FUNC_modPow
lw $t7, -20($sp)
lw $t8, -16($sp)
lw $t9, -12($sp)
addi $sp, $sp, -24
sw $v0, -60($sp)
lw $t7, -60($sp)
addi $sp, $sp, 24
sw $t7, -20($sp)
sw $t8, -16($sp)
sw $t9, -12($sp)

lw $a0, -84($sp)

sw $a0, -8($sp)

lw $a0, -64($sp)

sw $a0, -4($sp)

lw $a0, -72($sp)

sw $a0, 0($sp)
jal FUNC_modPow
lw $t7, -20($sp)
lw $t8, -16($sp)
lw $t9, -12($sp)
addi $sp, $sp, -24
sw $v0, -56($sp)
lw $t7, -56($sp)
li $v0, 1
lw $a0, -56($sp)

syscall
li $t7, 61
move $t8, $t7
sw $t8, -52($sp)
addi $sp, $sp, 24
sw $t7, -20($sp)
sw $t8, -16($sp)
sw $t9, -12($sp)

lw $a0, -76($sp)

sw $a0, -8($sp)

lw $a0, -52($sp)

sw $a0, -4($sp)

lw $a0, -68($sp)

sw $a0, 0($sp)
jal FUNC_modPow
lw $t7, -20($sp)
lw $t8, -16($sp)
lw $t9, -12($sp)
addi $sp, $sp, -24
sw $v0, -60($sp)
lw $t7, -60($sp)
addi $sp, $sp, 24
sw $t7, -20($sp)
sw $t8, -16($sp)
sw $t9, -12($sp)

lw $a0, -84($sp)

sw $a0, -8($sp)

lw $a0, -60($sp)

sw $a0, -4($sp)

lw $a0, -68($sp)

sw $a0, 0($sp)
jal FUNC_modPow
lw $t7, -20($sp)
lw $t8, -16($sp)
lw $t9, -12($sp)
addi $sp, $sp, -24
sw $v0, -56($sp)
lw $t7, -56($sp)
li $v0, 1
lw $a0, -56($sp)

syscall
lw $fp, -64($sp)
lw $ra, -68($sp)
addi $sp, $sp, -72
jr $ra

