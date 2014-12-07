.data
.text

.end main
.ent main
.globl main
main:
addi $sp, $sp, 36
sw $fp, -28($sp)
sw $ra, -32($sp)
li $t7, 1
move $t8, $t7
sw $t8, -24($sp)
li.s $f29, 1.5
mov.s $f30, $f29
swc1 $f30, -20($sp)
li $t7, 7
move $t8, $t7
sw $t8, -24($sp)
lw $t8, -24($sp)
move $t8, $t8
sw $t8, -24($sp)
li $t8, 5
lw $t7, -24($sp)
mul $t9, $t7, $t8
sw $t9, -16($sp)
lw $t7, -16($sp)
move $t8, $t7
sw $t8, -24($sp)
li $t7, 4
mtc1, $t7, $f30
cvt.s.w, $f30, $f30
mov.s $f29, $f30
swc1 $f29, -20($sp)
li.s $f29, 4.5
mov.s $f30, $f29
swc1 $f30, -20($sp)
li.s $f30, 4.5
mul.s $f31, $f30, $f30
swc1 $f31, -12($sp)
lwc1 $f29, -12($sp)
mov.s $f30, $f29
swc1 $f30, -20($sp)
li $t7, 3
lwc1 $f29, -20($sp)
mtc1, $t7, $f31
cvt.s.w, $f31, $f31
add.s $f30, $f29, $f31
swc1 $f30, -8($sp)
lwc1 $f29, -8($sp)
mov.s $f30, $f29
swc1 $f30, -20($sp)
li.s $f29, 4.5
lwc1 $f30, -20($sp)
mul.s $f31, $f29, $f30
swc1 $f31, -4($sp)
lwc1 $f29, -4($sp)
mov.s $f30, $f29
swc1 $f30, -20($sp)
lw $t7, -24($sp)
mtc1, $t7, $f30
cvt.s.w, $f30, $f30
mov.s $f29, $f30
swc1 $f29, -20($sp)
lw $t7, -24($sp)
li.s $f29, 5.9
mtc1, $t7, $f31
cvt.s.w, $f31, $f31
mul.s $f30, $f31, $f29
swc1 $f30, 0($sp)
lwc1 $f29, 0($sp)
mov.s $f30, $f29
swc1 $f30, -20($sp)
lw $fp, -28($sp)
lw $ra, -32($sp)
addi $sp, $sp, -36
jr $ra

