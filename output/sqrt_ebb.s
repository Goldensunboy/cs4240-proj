.data
.text

.end main
.ent FUNC_sqrt
.globl FUNC_sqrt
FUNC_sqrt:
addi $sp, $sp, 64
swc1 $f4, -20($sp)
swc1 $f5, -16($sp)
swc1 $f6, -12($sp)
swc1 $f7, -8($sp)
swc1 $f8, -4($sp)
swc1 $f9, 0($sp)
sw $fp, -56($sp)
sw $ra, -60($sp)
lwc1 $f4, -52($sp)
li $t7, 1
mtc1 $t7, $f29
cvt.s.w $f29, $f29

mov.s $f4, $f29

swc1 $f4, -52($sp)

LABEL_WHILE_TOP_0:
lwc1 $f4, -48($sp)
lwc1 $f5, -52($sp)
lwc1 $f6, -64($sp)
lwc1 $f7, -44($sp)
lwc1 $f8, -40($sp)
lwc1 $f9, -36($sp)
mul.s $f8, $f5, $f5
sub.s $f4, $f8, $f6
li.s $f29, 0.001
swc1 $f4, -48($sp)
swc1 $f5, -52($sp)
swc1 $f6, -64($sp)
swc1 $f7, -44($sp)
swc1 $f8, -40($sp)
swc1 $f9, -36($sp)
c.le.s $f4, $f29
bc1f LABEL_WHILE_BEGIN_0

mul.s $f9, $f5, $f5
sub.s $f7, $f6, $f9
li.s $f29, 0.001
swc1 $f4, -48($sp)
swc1 $f5, -52($sp)
swc1 $f6, -64($sp)
swc1 $f7, -44($sp)
swc1 $f8, -40($sp)
swc1 $f9, -36($sp)
c.le.s $f7, $f29
bc1t LABEL_WHILE_END_0

LABEL_WHILE_BEGIN_0:
lwc1 $f4, -52($sp)
lwc1 $f5, -64($sp)
lwc1 $f6, -32($sp)
lwc1 $f7, -28($sp)
lwc1 $f8, -24($sp)
div.s $f8, $f5, $f4
add.s $f7, $f4, $f8
li $t7, 2
mtc1 $t7, $f29
cvt.s.w $f29, $f29
div.s $f6, $f7, $f29

mov.s $f4, $f6

swc1 $f4, -52($sp)
swc1 $f5, -64($sp)
swc1 $f6, -32($sp)
swc1 $f7, -28($sp)
swc1 $f8, -24($sp)
j LABEL_WHILE_TOP_0

LABEL_WHILE_END_0:
swc1 $f4, -48($sp)
swc1 $f5, -52($sp)
swc1 $f6, -64($sp)
swc1 $f7, -44($sp)
swc1 $f8, -40($sp)
swc1 $f9, -36($sp)
lwc1 $f0, -52($sp)
lw $fp, -56($sp)
lw $ra, -60($sp)
lwc1 $f4, -20($sp)
lwc1 $f5, -16($sp)
lwc1 $f6, -12($sp)
lwc1 $f7, -8($sp)
lwc1 $f8, -4($sp)
lwc1 $f9, 0($sp)
addi $sp, $sp, -64
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

