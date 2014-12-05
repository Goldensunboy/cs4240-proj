.data
.text
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
lw $t0, -104($sp)
lw $t1, -96($sp)
lw $t2, -100($sp)
li $t7, 6
move $t2, $t7
li $t7, 2
move $t1, $t7
li $t7, 1
move $t0, $t7

lw $t0, -104($sp)
lw $t1, -96($sp)
LABEL_FOR_START_0:
sw $t0, -104($sp)
sw $t1, -96($sp)
sw $t0, -104($sp)
sw $t1, -96($sp)
bgt  $t0, $t1, LABEL_FOR_END_0
sw $t0, -104($sp)
sw $t1, -96($sp)

lw $t0, -104($sp)
lw $t1, -92($sp)
lw $t3, -100($sp)
lw $t2, -88($sp)
add $t1, $t3, $t3
sw $t0, -104($sp)
sw $t1, -92($sp)
sw $t3, -100($sp)
sw $t2, -88($sp)
move $t3, $t1
sw $t0, -104($sp)
sw $t1, -92($sp)
sw $t3, -100($sp)
sw $t2, -88($sp)
li $t7, 1
add $t2, $t0, $t7
sw $t0, -104($sp)
sw $t1, -92($sp)
sw $t3, -100($sp)
sw $t2, -88($sp)
move $t0, $t2
sw $t0, -104($sp)
sw $t1, -92($sp)
sw $t3, -100($sp)
sw $t2, -88($sp)
sw $t0, -104($sp)
sw $t1, -92($sp)
sw $t3, -100($sp)
sw $t2, -88($sp)
j LABEL_FOR_START_0
sw $t0, -104($sp)
sw $t1, -92($sp)
sw $t3, -100($sp)
sw $t2, -88($sp)

LABEL_FOR_END_0:
lw $fp, -108($sp)
lw $ra, -112($sp)
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
addi $sp, $sp, -116
jr $ra

