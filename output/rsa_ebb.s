.data
.text
.ent FUNC_mod
.globl FUNC_mod
FUNC_mod:
addi $sp, $sp, 16
sw $fp, -8($sp)
sw $ra, -12($sp)
lw $t0, -4($sp)
li $t7, 0

move $t0, $t7

sw $t0, -4($sp)

LABEL_WHILE_TOP_0:
lw $t1, -16($sp)
lw $t0, 0($sp)
lw $t2, -20($sp)
blt  $t2, $t1, LABEL_WHILE_END_0

sub $t0, $t2, $t1

move $t2, $t0

sw $t1, -16($sp)
sw $t0, 0($sp)
sw $t2, -20($sp)
j LABEL_WHILE_TOP_0

LABEL_WHILE_END_0:
sw $t1, -16($sp)
sw $t0, 0($sp)
sw $t2, -20($sp)
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
lw $t0, -8($sp)
lw $t1, -4($sp)
lw $t2, 0($sp)
lw $t3, -20($sp)
lw $t4, -24($sp)
li $t7, 0

move $t1, $t7

li $t7, 0
bne  $t3, $t7, LABEL_ELSE_BEGIN_0

sw $t0, -8($sp)
sw $t1, -4($sp)
sw $t2, 0($sp)
sw $t3, -20($sp)
sw $t4, -24($sp)
li $v0, 1
lw $fp, -12($sp)
lw $ra, -16($sp)
addi $sp, $sp, -20
jr $ra
sw $t0, -8($sp)
sw $t1, -4($sp)
sw $t2, 0($sp)
sw $t3, -20($sp)
sw $t4, -24($sp)
j LABEL_ELSE_END_0

LABEL_ELSE_BEGIN_0:
li $t7, 1
sub $t2, $t3, $t7
sw $t0, -8($sp)
sw $t1, -4($sp)
sw $t2, 0($sp)
sw $t3, -20($sp)
sw $t4, -24($sp)
addi $sp, $sp, 32
sw $t0, -28($sp)
sw $t1, -24($sp)
sw $t2, -20($sp)
sw $t3, -16($sp)
sw $t4, -12($sp)
sw $t7, -8($sp)

lw $a0, -56($sp)

sw $a0, -4($sp)

lw $a0, -32($sp)

sw $a0, 0($sp)
jal FUNC_pow
lw $t0, -28($sp)
lw $t1, -24($sp)
lw $t2, -20($sp)
lw $t3, -16($sp)
lw $t4, -12($sp)
lw $t7, -8($sp)
addi $sp, $sp, -32
sw $v0, -4($sp)
lw $t1, -4($sp)
mul $t0, $t4, $t1
sw $t0, -8($sp)
sw $t1, -4($sp)
sw $t2, 0($sp)
sw $t3, -20($sp)
sw $t4, -24($sp)
lw $v0, -8($sp)
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
lw $t0, -8($sp)
lw $t1, -4($sp)
lw $t2, 0($sp)
lw $t3, -24($sp)
lw $t4, -28($sp)
li $t7, 0

move $t1, $t7

li $t7, 0
bne  $t3, $t7, LABEL_ELSE_BEGIN_1

sw $t0, -8($sp)
sw $t1, -4($sp)
sw $t2, 0($sp)
sw $t3, -24($sp)
sw $t4, -28($sp)
li $v0, 1
lw $fp, -12($sp)
lw $ra, -16($sp)
addi $sp, $sp, -20
jr $ra
sw $t0, -8($sp)
sw $t1, -4($sp)
sw $t2, 0($sp)
sw $t3, -24($sp)
sw $t4, -28($sp)
j LABEL_ELSE_END_1

LABEL_ELSE_BEGIN_1:
li $t7, 1
sub $t0, $t3, $t7
sw $t0, -8($sp)
sw $t1, -4($sp)
sw $t2, 0($sp)
sw $t3, -24($sp)
sw $t4, -28($sp)
addi $sp, $sp, 36
sw $t0, -32($sp)
sw $t1, -28($sp)
sw $t2, -24($sp)
sw $t3, -20($sp)
sw $t4, -16($sp)
sw $t7, -12($sp)

lw $a0, -64($sp)

sw $a0, -8($sp)

lw $a0, -44($sp)

sw $a0, -4($sp)

lw $a0, -56($sp)

sw $a0, 0($sp)
jal FUNC_modPow
lw $t0, -32($sp)
lw $t1, -28($sp)
lw $t2, -24($sp)
lw $t3, -20($sp)
lw $t4, -16($sp)
lw $t7, -12($sp)
addi $sp, $sp, -36
sw $v0, -4($sp)
lw $t1, -4($sp)
mul $t2, $t4, $t1

move $t1, $t2

sw $t0, -8($sp)
sw $t1, -4($sp)
sw $t2, 0($sp)
sw $t3, -24($sp)
sw $t4, -28($sp)
addi $sp, $sp, 24
sw $t0, -20($sp)
sw $t7, -16($sp)
sw $t1, -12($sp)
sw $t2, -8($sp)

lw $a0, -28($sp)

sw $a0, -4($sp)

lw $a0, -44($sp)

sw $a0, 0($sp)
jal FUNC_mod
lw $t0, -20($sp)
lw $t7, -16($sp)
lw $t1, -12($sp)
lw $t2, -8($sp)
addi $sp, $sp, -24
sw $v0, -4($sp)
lw $t1, -4($sp)
sw $t0, -8($sp)
sw $t1, -4($sp)
sw $t2, 0($sp)
sw $t3, -24($sp)
sw $t4, -28($sp)
lw $v0, -4($sp)
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
lw $t0, -24($sp)
lw $t1, -8($sp)
lw $t2, -4($sp)
lw $t4, -20($sp)
lw $t3, 0($sp)
li $t7, 0

move $t2, $t7

bne  $t0, $t4, LABEL_ELSE_BEGIN_2

sw $t0, -24($sp)
sw $t1, -8($sp)
sw $t2, -4($sp)
sw $t4, -20($sp)
sw $t3, 0($sp)
lw $v0, -24($sp)
lw $fp, -12($sp)
lw $ra, -16($sp)
addi $sp, $sp, -20
jr $ra
sw $t0, -24($sp)
sw $t1, -8($sp)
sw $t2, -4($sp)
sw $t4, -20($sp)
sw $t3, 0($sp)
j LABEL_ELSE_END_2

LABEL_ELSE_BEGIN_2:
ble  $t0, $t4, LABEL_ELSE_BEGIN_3

sub $t1, $t0, $t4
sw $t0, -24($sp)
sw $t1, -8($sp)
sw $t2, -4($sp)
sw $t4, -20($sp)
sw $t3, 0($sp)
addi $sp, $sp, 32
sw $t0, -28($sp)
sw $t1, -24($sp)
sw $t2, -20($sp)
sw $t4, -16($sp)
sw $t3, -12($sp)
sw $t7, -8($sp)

lw $a0, -40($sp)

sw $a0, -4($sp)

lw $a0, -52($sp)

sw $a0, 0($sp)
jal FUNC_gcd
lw $t0, -28($sp)
lw $t1, -24($sp)
lw $t2, -20($sp)
lw $t4, -16($sp)
lw $t3, -12($sp)
lw $t7, -8($sp)
addi $sp, $sp, -32
sw $v0, -4($sp)
lw $t2, -4($sp)
sw $t0, -24($sp)
sw $t1, -8($sp)
sw $t2, -4($sp)
sw $t4, -20($sp)
sw $t3, 0($sp)
j LABEL_ELSE_END_3

LABEL_ELSE_BEGIN_3:
sub $t3, $t4, $t0
sw $t0, -24($sp)
sw $t1, -8($sp)
sw $t2, -4($sp)
sw $t4, -20($sp)
sw $t3, 0($sp)
addi $sp, $sp, 32
sw $t0, -28($sp)
sw $t1, -24($sp)
sw $t2, -20($sp)
sw $t4, -16($sp)
sw $t3, -12($sp)
sw $t7, -8($sp)

lw $a0, -56($sp)

sw $a0, -4($sp)

lw $a0, -32($sp)

sw $a0, 0($sp)
jal FUNC_gcd
lw $t0, -28($sp)
lw $t1, -24($sp)
lw $t2, -20($sp)
lw $t4, -16($sp)
lw $t3, -12($sp)
lw $t7, -8($sp)
addi $sp, $sp, -32
sw $v0, -4($sp)
lw $t2, -4($sp)

LABEL_ELSE_END_3:
lw $v0, -4($sp)
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
lw $t0, -12($sp)
lw $t1, -8($sp)
li $t7, 1

move $t1, $t7

li $t7, 1

move $t0, $t7

sw $t0, -12($sp)
sw $t1, -8($sp)

LABEL_WHILE_TOP_1:
lw $t1, -12($sp)
lw $t0, -4($sp)
lw $t2, 0($sp)
lw $t3, -8($sp)
lw $t4, -28($sp)
li $t7, 1000
bge  $t1, $t7, LABEL_WHILE_END_1

mul $t2, $t4, $t1
