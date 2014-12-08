.data
.text
.ent main
.globl main
main:
addi $sp, $sp, 12
sw $fp, -4($sp)
sw $ra, -8($sp)
li $t7, 0

move $t8, $t7

sw $t8, 0($sp)
li $v0, 2

li.s $f12, 9.0
syscall

lw $fp, -4($sp)
lw $ra, -8($sp)
addi $sp, $sp, -12
jr $ra

