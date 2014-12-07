.data
.text

.end main
.ent FUNC_sqrt
.globl FUNC_sqrt
FUNC_sqrt:
addi $sp, $sp, 40
sw $fp, -32($sp)
sw $ra, -36($sp)
li $t7, 1
mtc1 $t7, $f30
cvt.s.w $f30, $f30

mov.s $f29, $f30

swc1 $f29, -28($sp)
LABEL_WHILE_TOP_0:
lwc1 $f30, -28($sp)
mul.s $f31, $f30, $f30
swc1 $f31, -24($sp)
lwc1 $f30, -40($sp)
lwc1 $f29, -24($sp)
sub.s $f31, $f29, $f30
swc1 $f31, -20($sp)
lwc1 $f29, -20($sp)
li.s $f30, 0.001
c.le.s $f29, $f30
bc1f LABEL_WHILE_BEGIN_0
lwc1 $f30, -28($sp)
mul.s $f31, $f30, $f30
swc1 $f31, -16($sp)
lwc1 $f29, -40($sp)
lwc1 $f30, -16($sp)
sub.s $f31, $f29, $f30
swc1 $f31, -12($sp)
li.s $f30, 0.001
lwc1 $f29, -12($sp)
c.le.s $f29, $f30
bc1t LABEL_WHILE_END_0
LABEL_WHILE_BEGIN_0:
lwc1 $f29, -40($sp)
lwc1 $f30, -28($sp)
div.s $f31, $f29, $f30
swc1 $f31, -8($sp)
lwc1 $f30, -8($sp)
lwc1 $f29, -28($sp)
add.s $f31, $f29, $f30
swc1 $f31, -4($sp)
li $t7, 2
lwc1 $f29, -4($sp)
mtc1 $t7, $f31
cvt.s.w $f31, $f31
div.s $f30, $f29, $f31
swc1 $f30, 0($sp)
lwc1 $f29, 0($sp)

mov.s $f30, $f29

swc1 $f30, -28($sp)
j LABEL_WHILE_TOP_0
LABEL_WHILE_END_0:
lwc1 $f0, -28($sp)
lw $fp, -32($sp)
lw $ra, -36($sp)
addi $sp, $sp, -40
jr $ra

.end FUNC_sqrt
.ent main
.globl main
main:
addi $sp, $sp, 12
sw $fp, -4($sp)
sw $ra, -8($sp)
li $t7, 120
mtc1 $t7, $f30
cvt.s.w $f30, $f30

mov.s $f29, $f30

swc1 $f29, 0($sp)
addi $sp, $sp, 20
sw $t7, -16($sp)
swc1 $f30, -12($sp)
swc1 $f29, -8($sp)
swc1 $f31, -4($sp)

lwc1 $f12, -20($sp)

swc1 $f12, 0($sp)
jal FUNC_sqrt
lw $t7, -16($sp)
lwc1 $f30, -12($sp)
lwc1 $f29, -8($sp)
lwc1 $f31, -4($sp)
addi $sp, $sp, -20
swc1 $f0, 0($sp)
lwc1 $f29, 0($sp)
li $v0, 2

lwc1 $f12, 0($sp)
syscall

lw $fp, -4($sp)
lw $ra, -8($sp)
addi $sp, $sp, -12
jr $ra

