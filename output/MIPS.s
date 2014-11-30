.data
.text
.globl main
main:
addi $sp, $sp, 128
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
sw $fp, -120($sp)
sw $ra, -124($sp)
li $t0, 1
sw $t0, -116($sp)
li $t0, 2
sw $t0, -112($sp)
li $t0, 3
sw $t0, -108($sp)
li $t0, 4
sw $t0, -104($sp)
li.s $f16, 5.0
swc1 $f16, -100($sp)
li.s $f16, 6.0
swc1 $f16, -96($sp)
li.s $f16, 7.0
swc1 $f16, -92($sp)
li.s $f16, 8.0
swc1 $f16, -88($sp)

li $t0, 19
sw $t0, -116($sp)
li $t9, 11
lw $t0, -116($sp)
add $t1, $t9, $t0
sw $t1, -112($sp)
lw $t0, -116($sp)
lw $t1, -112($sp)
add $t2, $t0, $t1
sw $t2, -108($sp)

li.s $f16, 20.0
swc1 $f16, -100($sp)
li.s $f31, 11.0
lwc1 $f16, -100($sp)
add.s $f17, $f31, $f16
swc1 $f17, -96($sp)
lwc1 $f16, -100($sp)
lwc1 $f17, -96($sp)
add.s $f18, $f16, $f17
swc1 $f18, -92($sp)

li.s $f19, 19.5
swc1 $f19, -88($sp)
li.s $f31, 9.0
lwc1 $f16, -88($sp)
add.s $f20, $f31, $f16
swc1 $f20, -92($sp)
li.s $f31, 9.0
lwc1 $f16, -88($sp)
add.s $f21, $f31, $f16
swc1 $f21, -92($sp)
li.s $f31, 7.5
lw $t0, -116($sp)
mtc1 $t0, $f30
cvt.s.w $f30, $f30
add.s $f22, $f31, $f30
swc1 $f22, -92($sp)
li.s $f31, 7.5
lw $t0, -116($sp)
mtc1 $t0, $f30
cvt.s.w $f30, $f30
add.s $f23, $f31, $f30
swc1 $f23, -92($sp)
lwc1 $f16, -96($sp)
lw $t0, -112($sp)
mtc1 $t0, $f31
cvt.s.w $f31, $f31
add.s $f24, $f16, $f31
swc1 $f24, -92($sp)
lw $t0, -108($sp)
lwc1 $f16, -96($sp)
mtc1 $t0, $f30
cvt.s.w $f30, $f30
add.s $f25, $f30, $f16
swc1 $f25, -92($sp)
lw $t0, -116($sp)
lw $t1, -112($sp)
mtc1 $t0, $f30
cvt.s.w $f30, $f30
mtc1 $t1, $f31
cvt.s.w $f31, $f31
add.s $f26, $f30, $f31
swc1 $f26, -92($sp)
lw $fp, -120($sp)
lw $ra, -124($sp)
lw $s0, -84($sp)
lw $s1, -80($sp)
lw $s2, -76($sp)
lw $s3, -72($sp)
lw $s4, -68($sp)
lw $s5, -64($sp)
lw $s6, -60($sp)
lwc1 $f16, -56($sp)
lwc1 $f17, -52($sp)
lwc1 $f18, -48($sp)
lwc1 $f19, -44($sp)
lwc1 $f20, -40($sp)
lwc1 $f21, -36($sp)
lwc1 $f22, -32($sp)
lwc1 $f23, -28($sp)
lwc1 $f24, -24($sp)
lwc1 $f25, -20($sp)
lwc1 $f26, -16($sp)
lwc1 $f27, -12($sp)
lwc1 $f28, -8($sp)
lwc1 $f29, -4($sp)
lwc1 $f30, 0($sp)
addi $sp, $sp, -128
jr $ra
.end main
