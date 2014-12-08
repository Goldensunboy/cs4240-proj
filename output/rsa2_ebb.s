.data
.text
.ent FUNC_mod
.globl FUNC_mod
FUNC_mod:
addi $sp, $sp, 16
sw $fp, -8($sp)
sw $ra, -12($sp)
lw $t0, -4($sp)
lw $t1, 0($sp)
li $t7, 0

move $t1, $t7

sub $t0, $t1, $t1

move $t1, $t0

sw $t0, -4($sp)
sw $t1, 0($sp)
lw $v0, 0($sp)
lw $fp, -8($sp)
lw $ra, -12($sp)
addi $sp, $sp, -16
jr $ra


.end FUNC_mod
.ent main
.globl main
main:
addi $sp, $sp, 24
sw $fp, -16($sp)
sw $ra, -20($sp)
lw $t0, -12($sp)
lw $t1, -8($sp)
lw $t3, -4($sp)
lw $t2, 0($sp)
li $t7, 3

move $t0, $t7

li $t7, 4

move $t1, $t7

li $t7, 4

move $t2, $t7

mul $t3, $t2, $t1
