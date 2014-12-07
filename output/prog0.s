.data
.text
.ent FUNC_max
.globl FUNC_max
FUNC_max:
addi $sp, $sp, 12
sw $fp, -4($sp)
sw $ra, -8($sp)
li $t7, 0

move $t8, $t7

sw $t8, 0($sp)
lw $t7, -16($sp)
lw $t8, -12($sp)
ble  $t7, $t8, LABEL_ELSE_BEGIN_0
lw $v0, -16($sp)
lw $fp, -4($sp)
lw $ra, -8($sp)
addi $sp, $sp, -12
jr $ra
j LABEL_ELSE_END_0
LABEL_ELSE_BEGIN_0:
lw $v0, -12($sp)
lw $fp, -4($sp)
lw $ra, -8($sp)
addi $sp, $sp, -12
jr $ra
LABEL_ELSE_END_0:
