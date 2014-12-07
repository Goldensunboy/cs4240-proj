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
lwc1 $f4, -92($sp)
lwc1 $f5, -104($sp)
lw $t0, -96($sp)
lw $t1, -100($sp)
lwc1 $f6, -108($sp)
lwc1 $f7, -88($sp)
lw $t2, -112($sp)
li $t7, 6
move $t2, $t7
li $t7, 13
mtc1, $t7, $f29
cvt.s.w, $f29, $f29
mov.s $f5, $f29
li $t7, 13
mtc1, $t7, $f29
cvt.s.w, $f29, $f29
mov.s $f6, $f29
add $t1, $t2, $t2
move $t2, $t1
add $t0, $t2, $t2
mtc1, $t0, $f29
cvt.s.w, $f29, $f29
mov.s $f6, $f29
mtc1, $t2, $f29
cvt.s.w, $f29, $f29
add.s $f4, $f29, $f6
mov.s $f6, $f4
add.s $f7, $f6, $f5
mov.s $f5, $f7
move $t2, $t2
mov.s $f6, $f6
mov.s $f5, $f5
swc1 $f4, -92($sp)
swc1 $f5, -104($sp)
sw $t0, -96($sp)
sw $t1, -100($sp)
swc1 $f6, -108($sp)
swc1 $f7, -88($sp)
sw $t2, -112($sp)
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

