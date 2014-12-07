.data
.text

.end main
.ent main
.globl main
main:
addi $sp, $sp, 24
sw $fp, -16($sp)
sw $ra, -20($sp)
li $t7, 0
move $t8, $t7
sw $t8, -12($sp)
li $t7, 0
move $t8, $t7
sw $t8, -8($sp)
li $t7, 0
move $t8, $t7
sw $t8, -4($sp)
li $t7, 1
move $t8, $t7
sw $t8, -12($sp)
li $t7, 5
move $t8, $t7
sw $t8, -4($sp)
LABEL_WHILE_TOP_0:
li $t8, 0
bne  $t8, $t8, LABEL_WHILE_END_0
lw $t7, -8($sp)
lw $t8, -4($sp)
ble  $t7, $t8, LABEL_ELSE_BEGIN_0
j LABEL_WHILE_END_0
j LABEL_ELSE_END_0
LABEL_ELSE_BEGIN_0:
LABEL_ELSE_END_0:
lw $t8, -8($sp)
lw $t7, -12($sp)
mul $t9, $t7, $t8
sw $t9, 0($sp)
lw $t7, 0($sp)
move $t8, $t7
sw $t8, -12($sp)
j LABEL_WHILE_TOP_0
LABEL_WHILE_END_0:
lw $fp, -16($sp)
lw $ra, -20($sp)
addi $sp, $sp, -24
jr $ra

