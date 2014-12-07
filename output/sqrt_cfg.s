.data
.text

.end main
.ent FUNC_sqrt
.globl FUNC_sqrt
FUNC_sqrt:
addi $sp, $sp, 60
swc1 $f4, -16($sp)
swc1 $f5, -12($sp)
swc1 $f6, -8($sp)
swc1 $f7, -4($sp)
swc1 $f8, 0($sp)
sw $fp, -52($sp)
sw $ra, -56($sp)
lwc1 $f4, -48($sp)
li $t7, 1
mtc1 $t7, $f29
cvt.s.w $f29, $f29

mov.s $f4, $f29

swc1 $f4, -48($sp)

LABEL_WHILE_TOP_0:
lwc1 $f4, -44($sp)
lwc1 $f5, -48($sp)
lwc1 $f6, -60($sp)
lwc1 $f7, -40($sp)
mul.s $f7, $f5, $f5
sub.s $f4, $f7, $f6
li.s $f29, 0.001
swc1 $f4, -44($sp)
swc1 $f5, -48($sp)
swc1 $f6, -60($sp)
swc1 $f7, -40($sp)
c.le.s $f4, $f29
bc1f LABEL_WHILE_BEGIN_0

lwc1 $f4, -48($sp)
lwc1 $f5, -60($sp)
lwc1 $f6, -36($sp)
lwc1 $f7, -32($sp)
mul.s $f7, $f4, $f4
sub.s $f6, $f5, $f7
li.s $f29, 0.001
swc1 $f4, -48($sp)
swc1 $f5, -60($sp)
swc1 $f6, -36($sp)
swc1 $f7, -32($sp)
c.le.s $f6, $f29
bc1t LABEL_WHILE_END_0

LABEL_WHILE_BEGIN_0:
lwc1 $f4, -48($sp)
lwc1 $f5, -28($sp)
lwc1 $f6, -60($sp)
lwc1 $f7, -24($sp)
lwc1 $f8, -20($sp)
div.s $f8, $f6, $f4
add.s $f7, $f4, $f8
li $t7, 2
mtc1 $t7, $f29
cvt.s.w $f29, $f29
div.s $f5, $f7, $f29

mov.s $f4, $f5

swc1 $f4, -48($sp)
swc1 $f5, -28($sp)
swc1 $f6, -60($sp)
swc1 $f7, -24($sp)
swc1 $f8, -20($sp)
j LABEL_WHILE_TOP_0

LABEL_WHILE_END_0:
lwc1 $f0, -48($sp)
lw $fp, -52($sp)
lw $ra, -56($sp)
lwc1 $f4, -16($sp)
lwc1 $f5, -12($sp)
lwc1 $f6, -8($sp)
lwc1 $f7, -4($sp)
lwc1 $f8, 0($sp)
addi $sp, $sp, -60
jr $ra


.end FUNC_sqrt
.ent main
.globl main
main:
addi $sp, $sp, 16
swc1 $f4, 0($sp)
sw $fp, -8($sp)
sw $ra, -12($sp)
lwc1 $f4, -4($sp)
li $t7, 120
mtc1 $t7, $f29
cvt.s.w $f29, $f29

mov.s $f4, $f29

swc1 $f4, -4($sp)
addi $sp, $sp, 12
sw $t7, -8($sp)
swc1 $f29, -4($sp)

lwc1 $f12, -16($sp)

swc1 $f12, 0($sp)
jal FUNC_sqrt
lw $t7, -8($sp)
lwc1 $f29, -4($sp)
addi $sp, $sp, -12
swc1 $f0, -4($sp)
lwc1 $f4, -4($sp)
swc1 $f4, -4($sp)
li $v0, 2

lwc1 $f12, -4($sp)
syscall

swc1 $f4, -4($sp)
lw $fp, -8($sp)
lw $ra, -12($sp)
lwc1 $f4, 0($sp)
addi $sp, $sp, -16
jr $ra

