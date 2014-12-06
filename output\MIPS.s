.data
.text

.end main
.ent main
.globl main
main:
addi $sp, $sp, 116
sw $s0, -84($sp)
sw $s1, -80($sp)
sw $s2, -76($sp)
sw $s3, -72($sp)
sw $s4, -68($sp)
sw $s5, -64($sp)
sw $s6, -60($sp)
swc1 $f16, -56($sp)
swc1 $f17, -52($sp)
swc1 $f18, -48($sp)
swc1 $f19, -44($sp)
swc1 $f20, -40($sp)
swc1 $f21, -36($sp)
swc1 $f22, -32($sp)
swc1 $f23, -28($sp)
swc1 $f24, -24($sp)
swc1 $f25, -20($sp)
swc1 $f26, -16($sp)
swc1 $f27, -12($sp)
swc1 $f28, -8($sp)
swc1 $f29, -4($sp)
swc1 $f30, 0($sp)
sw $fp, -108($sp)
sw $ra, -112($sp)
lw $t0, -100($sp)
li $t7, 0
move $t0, $t7
li $t7, 1
sw $t0, -100($sp)
bge  $t0, $t7, LABEL_ELSE_BEGIN_0

lw $t0, -100($sp)
li $t7, 1
move $t0, $t7
sw $t0, -100($sp)
j LABEL_ELSE_END_0

LABEL_ELSE_BEGIN_0:
lw $t0, -100($sp)
li $t7, 2
move $t0, $t7
sw $t0, -100($sp)

LABEL_ELSE_END_0:
lw $t0, -100($sp)
li $t7, 1
sw $t0, -100($sp)
ble  $t0, $t7, LABEL_ELSE_BEGIN_1

lw $t0, -100($sp)
li $t7, 3
move $t0, $t7
sw $t0, -100($sp)
j LABEL_ELSE_END_1

LABEL_ELSE_BEGIN_1:

LABEL_ELSE_END_1:
lw $t0, -100($sp)
li $t7, 1
sw $t0, -100($sp)
bne  $t0, $t7, LABEL_ELSE_BEGIN_2

lw $t0, -100($sp)
li $t7, 4
move $t0, $t7
sw $t0, -100($sp)
j LABEL_ELSE_END_2

LABEL_ELSE_BEGIN_2:

LABEL_ELSE_END_2:
lw $t0, -100($sp)
li $t7, 1
sw $t0, -100($sp)
beq  $t0, $t7, LABEL_ELSE_BEGIN_3

lw $t0, -100($sp)
li $t7, 5
move $t0, $t7
sw $t0, -100($sp)
j LABEL_ELSE_END_3

LABEL_ELSE_BEGIN_3:

LABEL_ELSE_END_3:
lw $t0, -100($sp)
li $t7, 1
sw $t0, -100($sp)
blt  $t0, $t7, LABEL_ELSE_BEGIN_4

lw $t0, -100($sp)
li $t7, 6
move $t0, $t7
sw $t0, -100($sp)
j LABEL_ELSE_END_4

LABEL_ELSE_BEGIN_4:

LABEL_ELSE_END_4:
lw $t0, -100($sp)
li $t7, 1
sw $t0, -100($sp)
blt  $t0, $t7, LABEL_ELSE_BEGIN_5

lw $t0, -100($sp)
li $t7, 7
move $t0, $t7
sw $t0, -100($sp)
j LABEL_ELSE_END_5

LABEL_ELSE_BEGIN_5:

LABEL_ELSE_END_5:
lw $t0, -92($sp)
