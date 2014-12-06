.data
.text
.ent main
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
li $t7, 6
move $t8, $t7
sw $t8, -112($sp)
li $t7, 13
mtc1, $t7, $f30
cvt.s.w, $f30, $f30
mov.s $f29, $f30
swc1 $f29, -104($sp)
li $t7, 13
mtc1, $t7, $f30
cvt.s.w, $f30, $f30
mov.s $f29, $f30
swc1 $f29, -108($sp)
lw $t8, -112($sp)
add $t9, $t8, $t8
sw $t9, -100($sp)
lw $t7, -100($sp)
move $t8, $t7
sw $t8, -112($sp)
lw $t8, -112($sp)
add $t9, $t8, $t8
sw $t9, -96($sp)
lw $t7, -96($sp)
mtc1, $t7, $f30
cvt.s.w, $f30, $f30
mov.s $f29, $f30
swc1 $f29, -108($sp)
lwc1 $f29, -108($sp)
lw $t7, -112($sp)
mtc1, $t7, $f31
cvt.s.w, $f31, $f31
add.s $f30, $f31, $f29
swc1 $f30, -92($sp)
lwc1 $f29, -92($sp)
mov.s $f30, $f29
swc1 $f30, -108($sp)
lwc1 $f30, -104($sp)
lwc1 $f29, -108($sp)
add.s $f31, $f29, $f30
swc1 $f31, -88($sp)
lwc1 $f29, -88($sp)
mov.s $f30, $f29
swc1 $f30, -104($sp)
lw $t8, -112($sp)
move $t8, $t8
sw $t8, -112($sp)
lwc1 $f30, -108($sp)
mov.s $f30, $f30
swc1 $f30, -108($sp)
lwc1 $f30, -104($sp)
mov.s $f30, $f30
swc1 $f30, -104($sp)
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

