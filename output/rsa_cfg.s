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
li $t7, 0

move $t0, $t7

sw $t0, -4($sp)

LABEL_WHILE_TOP_0:
lw $t0, -16($sp)
lw $t1, -20($sp)
sw $t0, -16($sp)
sw $t1, -20($sp)
blt  $t1, $t0, LABEL_WHILE_END_0

lw $t1, 0($sp)
lw $t0, -16($sp)
lw $t2, -20($sp)
sub $t1, $t2, $t0

move $t2, $t1

sw $t1, 0($sp)
sw $t0, -16($sp)
sw $t2, -20($sp)
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
lw $t0, -8($sp)
lw $t1, -20($sp)
li $t7, 0

move $t0, $t7

li $t7, 0
sw $t0, -8($sp)
sw $t1, -20($sp)
bne  $t1, $t7, LABEL_ELSE_BEGIN_0

li $v0, 1
lw $fp, -12($sp)
lw $ra, -16($sp)
addi $sp, $sp, -20
jr $ra
j LABEL_ELSE_END_0

LABEL_ELSE_BEGIN_0:
lw $t0, -4($sp)
lw $t1, -8($sp)
lw $t2, 0($sp)
lw $t3, -20($sp)
lw $t4, -24($sp)
li $t7, 1
sub $t2, $t3, $t7
sw $t0, -4($sp)
sw $t1, -8($sp)
sw $t2, 0($sp)
sw $t3, -20($sp)
sw $t4, -24($sp)
addi $sp, $sp, 32
sw $t0, -28($sp)
sw $t1, -24($sp)
sw $t7, -20($sp)
sw $t2, -16($sp)
sw $t3, -12($sp)
sw $t4, -8($sp)

lw $a0, -56($sp)

sw $a0, -4($sp)

lw $a0, -32($sp)

sw $a0, 0($sp)
jal FUNC_pow
lw $t0, -28($sp)
lw $t1, -24($sp)
lw $t7, -20($sp)
lw $t2, -16($sp)
lw $t3, -12($sp)
lw $t4, -8($sp)
addi $sp, $sp, -32
sw $v0, -8($sp)
lw $t1, -8($sp)
mul $t0, $t4, $t1
sw $t0, -4($sp)
sw $t1, -8($sp)
sw $t2, 0($sp)
sw $t3, -20($sp)
sw $t4, -24($sp)
lw $v0, -4($sp)
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
lw $t1, -24($sp)
li $t7, 0

move $t0, $t7

li $t7, 0
sw $t0, -8($sp)
sw $t1, -24($sp)
bne  $t1, $t7, LABEL_ELSE_BEGIN_1

li $v0, 1
lw $fp, -12($sp)
lw $ra, -16($sp)
addi $sp, $sp, -20
jr $ra
j LABEL_ELSE_END_1

LABEL_ELSE_BEGIN_1:
lw $t0, -4($sp)
lw $t1, -8($sp)
lw $t2, 0($sp)
lw $t3, -24($sp)
lw $t5, -28($sp)
lw $t4, -20($sp)
li $t7, 1
sub $t0, $t3, $t7
sw $t0, -4($sp)
sw $t1, -8($sp)
sw $t2, 0($sp)
sw $t3, -24($sp)
sw $t5, -28($sp)
sw $t4, -20($sp)
addi $sp, $sp, 40
sw $t0, -36($sp)
sw $t1, -32($sp)
sw $t7, -28($sp)
sw $t2, -24($sp)
sw $t3, -20($sp)
sw $t5, -16($sp)
sw $t4, -12($sp)

lw $a0, -68($sp)

sw $a0, -8($sp)

lw $a0, -44($sp)

sw $a0, -4($sp)

lw $a0, -60($sp)

sw $a0, 0($sp)
jal FUNC_modPow
lw $t0, -36($sp)
lw $t1, -32($sp)
lw $t7, -28($sp)
lw $t2, -24($sp)
lw $t3, -20($sp)
lw $t5, -16($sp)
lw $t4, -12($sp)
addi $sp, $sp, -40
sw $v0, -8($sp)
lw $t1, -8($sp)
mul $t2, $t5, $t1

move $t1, $t2

sw $t0, -4($sp)
sw $t1, -8($sp)
sw $t2, 0($sp)
sw $t3, -24($sp)
sw $t5, -28($sp)
sw $t4, -20($sp)
addi $sp, $sp, 24
sw $t0, -20($sp)
sw $t7, -16($sp)
sw $t1, -12($sp)
sw $t2, -8($sp)

lw $a0, -32($sp)

sw $a0, -4($sp)

lw $a0, -44($sp)

sw $a0, 0($sp)
jal FUNC_mod
lw $t0, -20($sp)
lw $t7, -16($sp)
lw $t1, -12($sp)
lw $t2, -8($sp)
addi $sp, $sp, -24
sw $v0, -8($sp)
lw $t1, -8($sp)
sw $t0, -4($sp)
sw $t1, -8($sp)
sw $t2, 0($sp)
sw $t3, -24($sp)
sw $t5, -28($sp)
sw $t4, -20($sp)
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
lw $t0, -24($sp)
lw $t1, -8($sp)
lw $t2, -20($sp)
li $t7, 0

move $t1, $t7

sw $t0, -24($sp)
sw $t1, -8($sp)
sw $t2, -20($sp)
bne  $t0, $t2, LABEL_ELSE_BEGIN_2

lw $v0, -24($sp)
lw $fp, -12($sp)
lw $ra, -16($sp)
addi $sp, $sp, -20
jr $ra
j LABEL_ELSE_END_2

LABEL_ELSE_BEGIN_2:
lw $t0, -24($sp)
lw $t1, -20($sp)
sw $t0, -24($sp)
sw $t1, -20($sp)
ble  $t0, $t1, LABEL_ELSE_BEGIN_3

lw $t0, -24($sp)
lw $t1, -4($sp)
lw $t2, -8($sp)
lw $t3, -20($sp)
sub $t1, $t0, $t3
sw $t0, -24($sp)
sw $t1, -4($sp)
sw $t2, -8($sp)
sw $t3, -20($sp)
addi $sp, $sp, 28
sw $t0, -24($sp)
sw $t1, -20($sp)
sw $t2, -16($sp)
sw $t7, -12($sp)
sw $t3, -8($sp)

lw $a0, -32($sp)

sw $a0, -4($sp)

lw $a0, -48($sp)

sw $a0, 0($sp)
jal FUNC_gcd
lw $t0, -24($sp)
lw $t1, -20($sp)
lw $t2, -16($sp)
lw $t7, -12($sp)
lw $t3, -8($sp)
addi $sp, $sp, -28
sw $v0, -8($sp)
lw $t2, -8($sp)
sw $t0, -24($sp)
sw $t1, -4($sp)
sw $t2, -8($sp)
sw $t3, -20($sp)
j LABEL_ELSE_END_3

LABEL_ELSE_BEGIN_3:
lw $t0, -24($sp)
lw $t1, -8($sp)
lw $t3, 0($sp)
lw $t2, -20($sp)
sub $t3, $t2, $t0
sw $t0, -24($sp)
sw $t1, -8($sp)
sw $t3, 0($sp)
sw $t2, -20($sp)
addi $sp, $sp, 28
sw $t0, -24($sp)
sw $t1, -20($sp)
sw $t2, -16($sp)
sw $t7, -12($sp)
sw $t3, -8($sp)

lw $a0, -52($sp)

sw $a0, -4($sp)

lw $a0, -28($sp)

sw $a0, 0($sp)
jal FUNC_gcd
lw $t0, -24($sp)
lw $t1, -20($sp)
lw $t2, -16($sp)
lw $t7, -12($sp)
lw $t3, -8($sp)
addi $sp, $sp, -28
sw $v0, -8($sp)
lw $t1, -8($sp)

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
lw $t0, -12($sp)
lw $t1, -8($sp)
li $t7, 1

move $t1, $t7

li $t7, 1

move $t0, $t7

sw $t0, -12($sp)
sw $t1, -8($sp)

LABEL_WHILE_TOP_1:
lw $t0, -12($sp)
li $t7, 1000
sw $t0, -12($sp)
bge  $t0, $t7, LABEL_WHILE_END_1

lw $t0, -12($sp)
lw $t1, -4($sp)
lw $t2, -8($sp)
lw $t4, -28($sp)
lw $t3, -24($sp)
mul $t1, $t4, $t0
sw $t0, -12($sp)
sw $t1, -4($sp)
sw $t2, -8($sp)
sw $t4, -28($sp)
sw $t3, -24($sp)
addi $sp, $sp, 24
sw $t0, -20($sp)
sw $t7, -16($sp)
sw $t1, -12($sp)
sw $t2, -8($sp)

lw $a0, -28($sp)

sw $a0, -4($sp)

lw $a0, -48($sp)

sw $a0, 0($sp)
jal FUNC_mod
lw $t0, -20($sp)
lw $t7, -16($sp)
lw $t1, -12($sp)
lw $t2, -8($sp)
addi $sp, $sp, -24
sw $v0, -8($sp)
lw $t2, -8($sp)
li $t7, 1
sw $t0, -12($sp)
sw $t1, -4($sp)
sw $t2, -8($sp)
sw $t4, -28($sp)
sw $t3, -24($sp)
bne  $t2, $t7, LABEL_ELSE_BEGIN_4

lw $v0, -12($sp)
lw $fp, -16($sp)
lw $ra, -20($sp)
addi $sp, $sp, -24
jr $ra
j LABEL_ELSE_END_4

LABEL_ELSE_BEGIN_4:
lw $t1, 0($sp)
lw $t0, -12($sp)
li $t7, 1
add $t1, $t0, $t7

move $t0, $t1

sw $t1, 0($sp)
sw $t0, -12($sp)

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
addi $sp, $sp, 56
sw $s0, -8($sp)
sw $s2, -4($sp)
sw $s1, 0($sp)
sw $fp, -48($sp)
sw $ra, -52($sp)
lw $t0, -44($sp)
lw $t1, -40($sp)
lw $t2, -36($sp)
lw $t3, -32($sp)
lw $t4, -28($sp)
lw $t5, -24($sp)
lw $t6, -64($sp)
lw $s0, -20($sp)
lw $s2, -60($sp)
lw $s1, -16($sp)
li $t7, 0

move $t0, $t7

li $t7, 0

move $t2, $t7

li $t7, 0

move $t3, $t7

li $t7, 0

move $t1, $t7

li $t7, 1
sub $t5, $t6, $t7
li $t7, 1
sub $t4, $s2, $t7
mul $s0, $t5, $t4

move $t1, $s0

li $t7, 1
sub $s1, $t1, $t7

move $t3, $s1

sw $t0, -44($sp)
sw $t1, -40($sp)
sw $t2, -36($sp)
sw $t3, -32($sp)
sw $t4, -28($sp)
sw $t5, -24($sp)
sw $t6, -64($sp)
sw $s0, -20($sp)
sw $s2, -60($sp)
sw $s1, -16($sp)

LABEL_WHILE_TOP_2:
lw $t0, -32($sp)
li $t7, 1
sw $t0, -32($sp)
ble  $t0, $t7, LABEL_WHILE_END_2

lw $t0, -40($sp)
lw $t1, -44($sp)
lw $t2, -32($sp)
sw $t0, -40($sp)
sw $t1, -44($sp)
sw $t2, -32($sp)
addi $sp, $sp, 28
sw $t0, -24($sp)
sw $t1, -20($sp)
sw $t2, -16($sp)
sw $t7, -12($sp)
sw $t3, -8($sp)

lw $a0, -60($sp)

sw $a0, -4($sp)

lw $a0, -68($sp)

sw $a0, 0($sp)
jal FUNC_gcd
lw $t0, -24($sp)
lw $t1, -20($sp)
lw $t2, -16($sp)
lw $t7, -12($sp)
lw $t3, -8($sp)
addi $sp, $sp, -28
sw $v0, -44($sp)
lw $t1, -44($sp)
li $t7, 1
sw $t0, -40($sp)
sw $t1, -44($sp)
sw $t2, -32($sp)
bne  $t1, $t7, LABEL_ELSE_BEGIN_5

j LABEL_WHILE_END_2

j LABEL_ELSE_END_5

LABEL_ELSE_BEGIN_5:
lw $t0, -12($sp)
lw $t1, -32($sp)
li $t7, 1
sub $t0, $t1, $t7

move $t1, $t0

sw $t0, -12($sp)
sw $t1, -32($sp)

LABEL_ELSE_END_5:
j LABEL_WHILE_TOP_2

LABEL_WHILE_END_2:
lw $t0, -32($sp)
li $t7, 1
sw $t0, -32($sp)
bne  $t0, $t7, LABEL_ELSE_BEGIN_6

li $v0, 0
lw $fp, -48($sp)
lw $ra, -52($sp)
lw $s0, -8($sp)
lw $s2, -4($sp)
lw $s1, 0($sp)
addi $sp, $sp, -56
jr $ra
j LABEL_ELSE_END_6

LABEL_ELSE_BEGIN_6:
lw $t0, -56($sp)
li $t7, 0
sw $t0, -56($sp)
bne  $t0, $t7, LABEL_ELSE_BEGIN_7

lw $v0, -32($sp)
lw $fp, -48($sp)
lw $ra, -52($sp)
lw $s0, -8($sp)
lw $s2, -4($sp)
lw $s1, 0($sp)
addi $sp, $sp, -56
jr $ra
j LABEL_ELSE_END_7

LABEL_ELSE_BEGIN_7:

LABEL_ELSE_END_7:

LABEL_ELSE_END_6:
lw $t0, -40($sp)
lw $t1, -32($sp)
lw $t2, -36($sp)
sw $t0, -40($sp)
sw $t1, -32($sp)
sw $t2, -36($sp)
addi $sp, $sp, 32
sw $t0, -28($sp)
sw $t1, -24($sp)
sw $t7, -20($sp)
sw $t2, -16($sp)
sw $t4, -12($sp)
sw $t3, -8($sp)

lw $a0, -64($sp)

sw $a0, -4($sp)

lw $a0, -72($sp)

sw $a0, 0($sp)
jal FUNC_modularInverse
lw $t0, -28($sp)
lw $t1, -24($sp)
lw $t7, -20($sp)
lw $t2, -16($sp)
lw $t4, -12($sp)
lw $t3, -8($sp)
addi $sp, $sp, -32
sw $v0, -36($sp)
lw $t2, -36($sp)
sw $t0, -40($sp)
sw $t1, -32($sp)
sw $t2, -36($sp)
lw $v0, -36($sp)
lw $fp, -48($sp)
lw $ra, -52($sp)
lw $s0, -8($sp)
lw $s2, -4($sp)
lw $s1, 0($sp)
addi $sp, $sp, -56
jr $ra


.end FUNC_genkey
.ent main
.globl main
main:
addi $sp, $sp, 108
sw $s0, -32($sp)
swc1 $f4, -28($sp)
sw $s1, -24($sp)
sw $s3, -20($sp)
sw $s2, -16($sp)
sw $s4, -12($sp)
sw $s6, -8($sp)
sw $s5, -4($sp)
sw $s7, 0($sp)
sw $fp, -100($sp)
sw $ra, -104($sp)
lw $t0, -96($sp)
lw $t1, -92($sp)
lw $t2, -88($sp)
lw $t3, -84($sp)
lw $t4, -80($sp)
lw $t5, -76($sp)
lw $t6, -72($sp)
lw $s0, -68($sp)
lwc1 $f4, -64($sp)
lw $s1, -60($sp)
lw $s3, -56($sp)
lw $s2, -52($sp)
lw $s4, -48($sp)
lw $s6, -44($sp)
lw $s5, -40($sp)
lw $s7, -36($sp)
li $t7, 0

move $s6, $t7

li $t7, 0

move $t0, $t7

li $t7, 0

move $t4, $t7

li $t7, 0

move $s7, $t7

li $t7, 0

move $s1, $t7

li $t7, 0

move $s3, $t7

li $t7, 0

move $t3, $t7

li $t7, 0

move $s2, $t7

li $t7, 0

move $t2, $t7

li $t7, 5

move $t6, $t7

li $t7, 11

move $t1, $t7

li $t7, 3

move $s5, $t7

li $t7, 7

move $t5, $t7

li $t7, 0
mtc1 $t7, $f29
cvt.s.w $f29, $f29

mov.s $f4, $f29

mul $s0, $t6, $t1

move $s1, $s0

mul $s4, $s5, $t5

move $s7, $s4

sw $t0, -96($sp)
sw $t1, -92($sp)
sw $t2, -88($sp)
sw $t3, -84($sp)
sw $t4, -80($sp)
sw $t5, -76($sp)
sw $t6, -72($sp)
sw $s0, -68($sp)
swc1 $f4, -64($sp)
sw $s1, -60($sp)
sw $s3, -56($sp)
sw $s2, -52($sp)
sw $s4, -48($sp)
sw $s6, -44($sp)
sw $s5, -40($sp)
sw $s7, -36($sp)
addi $sp, $sp, 44
sw $t0, -40($sp)
sw $t1, -36($sp)
sw $t2, -32($sp)
sw $t3, -28($sp)
sw $t4, -24($sp)
sw $t5, -20($sp)
sw $t6, -16($sp)
sw $t7, -12($sp)

lw $a0, -116($sp)

sw $a0, -8($sp)

lw $a0, -136($sp)

sw $a0, -4($sp)

li $a0, 0
sw $a0, 0($sp)
jal FUNC_genkey
lw $t0, -40($sp)
lw $t1, -36($sp)
lw $t2, -32($sp)
lw $t3, -28($sp)
lw $t4, -24($sp)
lw $t5, -20($sp)
lw $t6, -16($sp)
lw $t7, -12($sp)
addi $sp, $sp, -44
sw $v0, -88($sp)
lw $t2, -88($sp)
sw $t0, -96($sp)
sw $t1, -92($sp)
sw $t2, -88($sp)
sw $t3, -84($sp)
sw $t4, -80($sp)
sw $t5, -76($sp)
sw $t6, -72($sp)
sw $s0, -68($sp)
swc1 $f4, -64($sp)
sw $s1, -60($sp)
sw $s3, -56($sp)
sw $s2, -52($sp)
sw $s4, -48($sp)
sw $s6, -44($sp)
sw $s5, -40($sp)
sw $s7, -36($sp)
addi $sp, $sp, 44
sw $t0, -40($sp)
sw $t1, -36($sp)
sw $t2, -32($sp)
sw $t3, -28($sp)
sw $t4, -24($sp)
sw $t5, -20($sp)
sw $t6, -16($sp)
sw $t7, -12($sp)

lw $a0, -116($sp)

sw $a0, -8($sp)

lw $a0, -136($sp)

sw $a0, -4($sp)

li $a0, 1
sw $a0, 0($sp)
jal FUNC_genkey
lw $t0, -40($sp)
lw $t1, -36($sp)
lw $t2, -32($sp)
lw $t3, -28($sp)
lw $t4, -24($sp)
lw $t5, -20($sp)
lw $t6, -16($sp)
lw $t7, -12($sp)
addi $sp, $sp, -44
sw $v0, -84($sp)
lw $t3, -84($sp)
sw $t0, -96($sp)
sw $t1, -92($sp)
sw $t2, -88($sp)
sw $t3, -84($sp)
sw $t4, -80($sp)
sw $t5, -76($sp)
sw $t6, -72($sp)
sw $s0, -68($sp)
swc1 $f4, -64($sp)
sw $s1, -60($sp)
sw $s3, -56($sp)
sw $s2, -52($sp)
sw $s4, -48($sp)
sw $s6, -44($sp)
sw $s5, -40($sp)
sw $s7, -36($sp)
addi $sp, $sp, 44
sw $t0, -40($sp)
sw $t1, -36($sp)
sw $t2, -32($sp)
sw $t3, -28($sp)
sw $t4, -24($sp)
sw $t5, -20($sp)
sw $t6, -16($sp)
sw $t7, -12($sp)

lw $a0, -84($sp)

sw $a0, -8($sp)

lw $a0, -120($sp)

sw $a0, -4($sp)

li $a0, 0
sw $a0, 0($sp)
jal FUNC_genkey
lw $t0, -40($sp)
lw $t1, -36($sp)
lw $t2, -32($sp)
lw $t3, -28($sp)
lw $t4, -24($sp)
lw $t5, -20($sp)
lw $t6, -16($sp)
lw $t7, -12($sp)
addi $sp, $sp, -44
sw $v0, -52($sp)
lw $s2, -52($sp)
sw $t0, -96($sp)
sw $t1, -92($sp)
sw $t2, -88($sp)
sw $t3, -84($sp)
sw $t4, -80($sp)
sw $t5, -76($sp)
sw $t6, -72($sp)
sw $s0, -68($sp)
swc1 $f4, -64($sp)
sw $s1, -60($sp)
sw $s3, -56($sp)
sw $s2, -52($sp)
sw $s4, -48($sp)
sw $s6, -44($sp)
sw $s5, -40($sp)
sw $s7, -36($sp)
addi $sp, $sp, 44
sw $t0, -40($sp)
sw $t1, -36($sp)
sw $t2, -32($sp)
sw $t3, -28($sp)
sw $t4, -24($sp)
sw $t5, -20($sp)
sw $t6, -16($sp)
sw $t7, -12($sp)

lw $a0, -84($sp)

sw $a0, -8($sp)

lw $a0, -120($sp)

sw $a0, -4($sp)

li $a0, 1
sw $a0, 0($sp)
jal FUNC_genkey
lw $t0, -40($sp)
lw $t1, -36($sp)
lw $t2, -32($sp)
lw $t3, -28($sp)
lw $t4, -24($sp)
lw $t5, -20($sp)
lw $t6, -16($sp)
lw $t7, -12($sp)
addi $sp, $sp, -44
sw $v0, -56($sp)
lw $s3, -56($sp)
li $t7, 9

move $t4, $t7

sw $t0, -96($sp)
sw $t1, -92($sp)
sw $t2, -88($sp)
sw $t3, -84($sp)
sw $t4, -80($sp)
sw $t5, -76($sp)
sw $t6, -72($sp)
sw $s0, -68($sp)
swc1 $f4, -64($sp)
sw $s1, -60($sp)
sw $s3, -56($sp)
sw $s2, -52($sp)
sw $s4, -48($sp)
sw $s6, -44($sp)
sw $s5, -40($sp)
sw $s7, -36($sp)
addi $sp, $sp, 40
sw $t0, -36($sp)
sw $t1, -32($sp)
sw $t7, -28($sp)
sw $t2, -24($sp)
sw $t3, -20($sp)
sw $t5, -16($sp)
sw $t4, -12($sp)

lw $a0, -120($sp)

sw $a0, -8($sp)

lw $a0, -92($sp)

sw $a0, -4($sp)

lw $a0, -76($sp)

sw $a0, 0($sp)
jal FUNC_modPow
lw $t0, -36($sp)
lw $t1, -32($sp)
lw $t7, -28($sp)
lw $t2, -24($sp)
lw $t3, -20($sp)
lw $t5, -16($sp)
lw $t4, -12($sp)
addi $sp, $sp, -40
sw $v0, -44($sp)
lw $s6, -44($sp)
sw $t0, -96($sp)
sw $t1, -92($sp)
sw $t2, -88($sp)
sw $t3, -84($sp)
sw $t4, -80($sp)
sw $t5, -76($sp)
sw $t6, -72($sp)
sw $s0, -68($sp)
swc1 $f4, -64($sp)
sw $s1, -60($sp)
sw $s3, -56($sp)
sw $s2, -52($sp)
sw $s4, -48($sp)
sw $s6, -44($sp)
sw $s5, -40($sp)
sw $s7, -36($sp)
addi $sp, $sp, 40
sw $t0, -36($sp)
sw $t1, -32($sp)
sw $t7, -28($sp)
sw $t2, -24($sp)
sw $t3, -20($sp)
sw $t5, -16($sp)
sw $t4, -12($sp)

lw $a0, -84($sp)

sw $a0, -8($sp)

lw $a0, -96($sp)

sw $a0, -4($sp)

lw $a0, -76($sp)

sw $a0, 0($sp)
jal FUNC_modPow
lw $t0, -36($sp)
lw $t1, -32($sp)
lw $t7, -28($sp)
lw $t2, -24($sp)
lw $t3, -20($sp)
lw $t5, -16($sp)
lw $t4, -12($sp)
addi $sp, $sp, -40
sw $v0, -96($sp)
lw $t0, -96($sp)
sw $t0, -96($sp)
sw $t1, -92($sp)
sw $t2, -88($sp)
sw $t3, -84($sp)
sw $t4, -80($sp)
sw $t5, -76($sp)
sw $t6, -72($sp)
sw $s0, -68($sp)
swc1 $f4, -64($sp)
sw $s1, -60($sp)
sw $s3, -56($sp)
sw $s2, -52($sp)
sw $s4, -48($sp)
sw $s6, -44($sp)
sw $s5, -40($sp)
sw $s7, -36($sp)
li $v0, 1
lw $a0, -96($sp)

syscall
li $t7, 4

move $t4, $t7

sw $t0, -96($sp)
sw $t1, -92($sp)
sw $t2, -88($sp)
sw $t3, -84($sp)
sw $t4, -80($sp)
sw $t5, -76($sp)
sw $t6, -72($sp)
sw $s0, -68($sp)
swc1 $f4, -64($sp)
sw $s1, -60($sp)
sw $s3, -56($sp)
sw $s2, -52($sp)
sw $s4, -48($sp)
sw $s6, -44($sp)
sw $s5, -40($sp)
sw $s7, -36($sp)
addi $sp, $sp, 40
sw $t0, -36($sp)
sw $t1, -32($sp)
sw $t7, -28($sp)
sw $t2, -24($sp)
sw $t3, -20($sp)
sw $t5, -16($sp)
sw $t4, -12($sp)

lw $a0, -120($sp)

sw $a0, -8($sp)

lw $a0, -128($sp)

sw $a0, -4($sp)

lw $a0, -100($sp)

sw $a0, 0($sp)
jal FUNC_modPow
lw $t0, -36($sp)
lw $t1, -32($sp)
lw $t7, -28($sp)
lw $t2, -24($sp)
lw $t3, -20($sp)
lw $t5, -16($sp)
lw $t4, -12($sp)
addi $sp, $sp, -40
sw $v0, -44($sp)
lw $s6, -44($sp)
sw $t0, -96($sp)
sw $t1, -92($sp)
sw $t2, -88($sp)
sw $t3, -84($sp)
sw $t4, -80($sp)
sw $t5, -76($sp)
sw $t6, -72($sp)
sw $s0, -68($sp)
swc1 $f4, -64($sp)
sw $s1, -60($sp)
sw $s3, -56($sp)
sw $s2, -52($sp)
sw $s4, -48($sp)
sw $s6, -44($sp)
sw $s5, -40($sp)
sw $s7, -36($sp)
addi $sp, $sp, 40
sw $t0, -36($sp)
sw $t1, -32($sp)
sw $t7, -28($sp)
sw $t2, -24($sp)
sw $t3, -20($sp)
sw $t5, -16($sp)
sw $t4, -12($sp)

lw $a0, -84($sp)

sw $a0, -8($sp)

lw $a0, -124($sp)

sw $a0, -4($sp)

lw $a0, -100($sp)

sw $a0, 0($sp)
jal FUNC_modPow
lw $t0, -36($sp)
lw $t1, -32($sp)
lw $t7, -28($sp)
lw $t2, -24($sp)
lw $t3, -20($sp)
lw $t5, -16($sp)
lw $t4, -12($sp)
addi $sp, $sp, -40
sw $v0, -96($sp)
lw $t0, -96($sp)
sw $t0, -96($sp)
sw $t1, -92($sp)
sw $t2, -88($sp)
sw $t3, -84($sp)
sw $t4, -80($sp)
sw $t5, -76($sp)
sw $t6, -72($sp)
sw $s0, -68($sp)
swc1 $f4, -64($sp)
sw $s1, -60($sp)
sw $s3, -56($sp)
sw $s2, -52($sp)
sw $s4, -48($sp)
sw $s6, -44($sp)
sw $s5, -40($sp)
sw $s7, -36($sp)
li $v0, 1
lw $a0, -96($sp)

syscall
sw $t0, -96($sp)
sw $t1, -92($sp)
sw $t2, -88($sp)
sw $t3, -84($sp)
sw $t4, -80($sp)
sw $t5, -76($sp)
sw $t6, -72($sp)
sw $s0, -68($sp)
swc1 $f4, -64($sp)
sw $s1, -60($sp)
sw $s3, -56($sp)
sw $s2, -52($sp)
sw $s4, -48($sp)
sw $s6, -44($sp)
sw $s5, -40($sp)
sw $s7, -36($sp)
lw $fp, -100($sp)
lw $ra, -104($sp)
lw $s0, -32($sp)
lwc1 $f4, -28($sp)
lw $s1, -24($sp)
lw $s3, -20($sp)
lw $s2, -16($sp)
lw $s4, -12($sp)
lw $s6, -8($sp)
lw $s5, -4($sp)
lw $s7, 0($sp)
addi $sp, $sp, -108
jr $ra

