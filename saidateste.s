@.formatting.string = private constant [4 x i8] c"%d\0A\00"
define i32 @main() {
entry:
  %tmp0 = alloca i32
  store i32 0, i32 * %tmp0
  %tmp1 = call i8 *  @malloc(i32 0)
  %tmp2 = bitcast i8 * %tmp1 to %class.TV *
  %tmp3 = call i32  @__Start_TV(%class.TV * %tmp2)
  %tmp4 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp5 = call i32 (i8 *, ...)* @printf(i8 * %tmp4, i32 %tmp3)
  %tmp6 = load i32 * %tmp0
  ret i32 %tmp6
}
%class.TV = type { }
define i32 @__Start_TV(%class.TV * %this) {
  %tmp7 = alloca %class.Tree *
  %tmp8 = alloca i1
  %tmp9 = alloca i32
  %tmp10 = alloca %class.MyVisitor *
  %tmp11 = call i8 *  @malloc(i32 30)
  %tmp12 = bitcast i8 * %tmp11 to %class.Tree *
  store %class.Tree * %tmp12, %class.Tree * * %tmp7
  %tmp13 = load %class.Tree * * %tmp7
  %tmp14 = call i1  @__Init_Tree(%class.Tree * %tmp13, i32 16)
  store i1 %tmp14, i1 * %tmp8
  %tmp15 = load %class.Tree * * %tmp7
  %tmp16 = call i1  @__Print_Tree(%class.Tree * %tmp15)
  store i1 %tmp16, i1 * %tmp8
  %tmp17 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp18 = call i32 (i8 *, ...)* @printf(i8 * %tmp17, i32 100000000)
  %tmp19 = load %class.Tree * * %tmp7
  %tmp20 = call i1  @__Insert_Tree(%class.Tree * %tmp19, i32 8)
  store i1 %tmp20, i1 * %tmp8
  %tmp21 = load %class.Tree * * %tmp7
  %tmp22 = call i1  @__Insert_Tree(%class.Tree * %tmp21, i32 24)
  store i1 %tmp22, i1 * %tmp8
  %tmp23 = load %class.Tree * * %tmp7
  %tmp24 = call i1  @__Insert_Tree(%class.Tree * %tmp23, i32 4)
  store i1 %tmp24, i1 * %tmp8
  %tmp25 = load %class.Tree * * %tmp7
  %tmp26 = call i1  @__Insert_Tree(%class.Tree * %tmp25, i32 12)
  store i1 %tmp26, i1 * %tmp8
  %tmp27 = load %class.Tree * * %tmp7
  %tmp28 = call i1  @__Insert_Tree(%class.Tree * %tmp27, i32 20)
  store i1 %tmp28, i1 * %tmp8
  %tmp29 = load %class.Tree * * %tmp7
  %tmp30 = call i1  @__Insert_Tree(%class.Tree * %tmp29, i32 28)
  store i1 %tmp30, i1 * %tmp8
  %tmp31 = load %class.Tree * * %tmp7
  %tmp32 = call i1  @__Insert_Tree(%class.Tree * %tmp31, i32 14)
  store i1 %tmp32, i1 * %tmp8
  %tmp33 = load %class.Tree * * %tmp7
  %tmp34 = call i1  @__Print_Tree(%class.Tree * %tmp33)
  store i1 %tmp34, i1 * %tmp8
  %tmp35 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp36 = call i32 (i8 *, ...)* @printf(i8 * %tmp35, i32 100000000)
  %tmp37 = call i8 *  @malloc(i32 16)
  %tmp38 = bitcast i8 * %tmp37 to %class.MyVisitor *
  store %class.MyVisitor * %tmp38, %class.MyVisitor * * %tmp10
  %tmp39 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp40 = call i32 (i8 *, ...)* @printf(i8 * %tmp39, i32 50000000)
  %tmp41 = load %class.Tree * * %tmp7
  %tmp42 = load %class.MyVisitor * * %tmp10
  %tmp43 = call i32  @__accept_Tree(%class.Tree * %tmp41, %class.MyVisitor * %tmp42)
  store i32 %tmp43, i32 * %tmp9
  %tmp44 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp45 = call i32 (i8 *, ...)* @printf(i8 * %tmp44, i32 100000000)
  %tmp46 = load %class.Tree * * %tmp7
  %tmp47 = call i32  @__Search_Tree(%class.Tree * %tmp46, i32 24)
  %tmp48 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp49 = call i32 (i8 *, ...)* @printf(i8 * %tmp48, i32 %tmp47)
  %tmp50 = load %class.Tree * * %tmp7
  %tmp51 = call i32  @__Search_Tree(%class.Tree * %tmp50, i32 12)
  %tmp52 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp53 = call i32 (i8 *, ...)* @printf(i8 * %tmp52, i32 %tmp51)
  %tmp54 = load %class.Tree * * %tmp7
  %tmp55 = call i32  @__Search_Tree(%class.Tree * %tmp54, i32 16)
  %tmp56 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp57 = call i32 (i8 *, ...)* @printf(i8 * %tmp56, i32 %tmp55)
  %tmp58 = load %class.Tree * * %tmp7
  %tmp59 = call i32  @__Search_Tree(%class.Tree * %tmp58, i32 50)
  %tmp60 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp61 = call i32 (i8 *, ...)* @printf(i8 * %tmp60, i32 %tmp59)
  %tmp62 = load %class.Tree * * %tmp7
  %tmp63 = call i32  @__Search_Tree(%class.Tree * %tmp62, i32 12)
  %tmp64 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp65 = call i32 (i8 *, ...)* @printf(i8 * %tmp64, i32 %tmp63)
  %tmp66 = load %class.Tree * * %tmp7
  %tmp67 = call i1  @__Delete_Tree(%class.Tree * %tmp66, i32 12)
  store i1 %tmp67, i1 * %tmp8
  %tmp68 = load %class.Tree * * %tmp7
  %tmp69 = call i1  @__Print_Tree(%class.Tree * %tmp68)
  store i1 %tmp69, i1 * %tmp8
  %tmp70 = load %class.Tree * * %tmp7
  %tmp71 = call i32  @__Search_Tree(%class.Tree * %tmp70, i32 12)
  %tmp72 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp73 = call i32 (i8 *, ...)* @printf(i8 * %tmp72, i32 %tmp71)
  ret i32 0
}
%class.Tree = type { %class.Tree *, %class.Tree *, i32, i1, i1, %class.Tree * }
define i1 @__Init_Tree(%class.Tree * %this, i32 %v_key) {
  %tmp74 = alloca i32
  store i32 %v_key, i32 * %tmp74
  %tmp75 = getelementptr %class.Tree * %this, i32 0, i32 2
  %tmp76 = load i32 * %tmp74
  store i32 %tmp76, i32 * %tmp75
  %tmp77 = getelementptr %class.Tree * %this, i32 0, i32 3
  store i1 false, i1 * %tmp77
  %tmp78 = getelementptr %class.Tree * %this, i32 0, i32 4
  store i1 false, i1 * %tmp78
  ret i1 true
}
define i1 @__SetRight_Tree(%class.Tree * %this, %class.Tree * %rn) {
  %tmp79 = alloca %class.Tree *
  store %class.Tree * %rn, %class.Tree * * %tmp79
  %tmp80 = getelementptr %class.Tree * %this, i32 0, i32 1
  %tmp81 = load %class.Tree * * %tmp79
  store %class.Tree * %tmp81, %class.Tree * * %tmp80
  ret i1 true
}
define i1 @__SetLeft_Tree(%class.Tree * %this, %class.Tree * %ln) {
  %tmp82 = alloca %class.Tree *
  store %class.Tree * %ln, %class.Tree * * %tmp82
  %tmp83 = getelementptr %class.Tree * %this, i32 0, i32 0
  %tmp84 = load %class.Tree * * %tmp82
  store %class.Tree * %tmp84, %class.Tree * * %tmp83
  ret i1 true
}
define %class.Tree * @__GetRight_Tree(%class.Tree * %this) {
  %tmp85 = getelementptr %class.Tree * %this, i32 0, i32 1
  %tmp86 = load %class.Tree * * %tmp85
  ret %class.Tree * %tmp86
}
define %class.Tree * @__GetLeft_Tree(%class.Tree * %this) {
  %tmp87 = getelementptr %class.Tree * %this, i32 0, i32 0
  %tmp88 = load %class.Tree * * %tmp87
  ret %class.Tree * %tmp88
}
define i32 @__GetKey_Tree(%class.Tree * %this) {
  %tmp89 = getelementptr %class.Tree * %this, i32 0, i32 2
  %tmp90 = load i32 * %tmp89
  ret i32 %tmp90
}
define i1 @__SetKey_Tree(%class.Tree * %this, i32 %v_key) {
  %tmp91 = alloca i32
  store i32 %v_key, i32 * %tmp91
  %tmp92 = getelementptr %class.Tree * %this, i32 0, i32 2
  %tmp93 = load i32 * %tmp91
  store i32 %tmp93, i32 * %tmp92
  ret i1 true
}
define i1 @__GetHas_Right_Tree(%class.Tree * %this) {
  %tmp94 = getelementptr %class.Tree * %this, i32 0, i32 4
  %tmp95 = load i1 * %tmp94
  ret i1 %tmp95
}
define i1 @__GetHas_Left_Tree(%class.Tree * %this) {
  %tmp96 = getelementptr %class.Tree * %this, i32 0, i32 3
  %tmp97 = load i1 * %tmp96
  ret i1 %tmp97
}
define i1 @__SetHas_Left_Tree(%class.Tree * %this, i1 %val) {
  %tmp98 = alloca i1
  store i1 %val, i1 * %tmp98
  %tmp99 = getelementptr %class.Tree * %this, i32 0, i32 3
  %tmp100 = load i1 * %tmp98
  store i1 %tmp100, i1 * %tmp99
  ret i1 true
}
define i1 @__SetHas_Right_Tree(%class.Tree * %this, i1 %val) {
  %tmp101 = alloca i1
  store i1 %val, i1 * %tmp101
  %tmp102 = getelementptr %class.Tree * %this, i32 0, i32 4
  %tmp103 = load i1 * %tmp101
  store i1 %tmp103, i1 * %tmp102
  ret i1 true
}
define i1 @__Compare_Tree(%class.Tree * %this, i32 %num1, i32 %num2) {
  %tmp104 = alloca i32
  store i32 %num1, i32 * %tmp104
  %tmp105 = alloca i32
  store i32 %num2, i32 * %tmp105
  %tmp106 = alloca i1
  %tmp107 = alloca i32
  store i1 false, i1 * %tmp106
  %tmp108 = load i32 * %tmp105
  %tmp109 = add i32 %tmp108, 1
  store i32 %tmp109, i32 * %tmp107
  %tmp110 = load i32 * %tmp104
  %tmp111 = load i32 * %tmp105
  %tmp112 = icmp slt i32 %tmp110, %tmp111
  br i1 %tmp112, label %label0, label %label1
label0:
  store i1 false, i1 * %tmp106
  br label %label2
label1:
  %tmp113 = load i32 * %tmp104
  %tmp114 = load i32 * %tmp107
  %tmp115 = icmp slt i32 %tmp113, %tmp114
  %tmp116 = zext i1 %tmp115 to i32
  %tmp117 = add i32 %tmp116, 1
  %tmp118 = trunc i32 %tmp117 to i1
  br i1 %tmp118, label %label3, label %label4
label3:
  store i1 false, i1 * %tmp106
  br label %label5
label4:
  store i1 true, i1 * %tmp106
  br label %label5
label5:
  br label %label2
label2:
  %tmp119 = load i1 * %tmp106
  ret i1 %tmp119
}
define i1 @__Insert_Tree(%class.Tree * %this, i32 %v_key) {
  %tmp120 = alloca i32
  store i32 %v_key, i32 * %tmp120
  %tmp121 = alloca %class.Tree *
  %tmp122 = alloca i1
  %tmp123 = alloca %class.Tree *
  %tmp124 = alloca i1
  %tmp125 = alloca i32
  %tmp126 = call i8 *  @malloc(i32 30)
  %tmp127 = bitcast i8 * %tmp126 to %class.Tree *
  store %class.Tree * %tmp127, %class.Tree * * %tmp121
  %tmp128 = load %class.Tree * * %tmp121
  %tmp129 = load i32 * %tmp120
  %tmp130 = call i1  @__Init_Tree(%class.Tree * %tmp128, i32 %tmp129)
  store i1 %tmp130, i1 * %tmp122
  store %class.Tree * %this, %class.Tree * * %tmp123
  store i1 true, i1 * %tmp124
  br label %label6
label6:
  %tmp131 = load i1 * %tmp124
  br i1 %tmp131, label %label7, label %label8
label7:
  %tmp132 = load %class.Tree * * %tmp123
  %tmp133 = call i32  @__GetKey_Tree(%class.Tree * %tmp132)
  store i32 %tmp133, i32 * %tmp125
  %tmp134 = load i32 * %tmp120
  %tmp135 = load i32 * %tmp125
  %tmp136 = icmp slt i32 %tmp134, %tmp135
  br i1 %tmp136, label %label9, label %label10
label9:
  %tmp137 = load %class.Tree * * %tmp123
  %tmp138 = call i1  @__GetHas_Left_Tree(%class.Tree * %tmp137)
  br i1 %tmp138, label %label12, label %label13
label12:
  %tmp139 = load %class.Tree * * %tmp123
  %tmp140 = call %class.Tree *  @__GetLeft_Tree(%class.Tree * %tmp139)
  store %class.Tree * %tmp140, %class.Tree * * %tmp123
  br label %label14
label13:
  store i1 false, i1 * %tmp124
  %tmp141 = load %class.Tree * * %tmp123
  %tmp142 = call i1  @__SetHas_Left_Tree(%class.Tree * %tmp141, i1 true)
  store i1 %tmp142, i1 * %tmp122
  %tmp143 = load %class.Tree * * %tmp123
  %tmp144 = load %class.Tree * * %tmp121
  %tmp145 = call i1  @__SetLeft_Tree(%class.Tree * %tmp143, %class.Tree * %tmp144)
  store i1 %tmp145, i1 * %tmp122
  br label %label14
label14:
  br label %label11
label10:
  %tmp146 = load %class.Tree * * %tmp123
  %tmp147 = call i1  @__GetHas_Right_Tree(%class.Tree * %tmp146)
  br i1 %tmp147, label %label15, label %label16
label15:
  %tmp148 = load %class.Tree * * %tmp123
  %tmp149 = call %class.Tree *  @__GetRight_Tree(%class.Tree * %tmp148)
  store %class.Tree * %tmp149, %class.Tree * * %tmp123
  br label %label17
label16:
  store i1 false, i1 * %tmp124
  %tmp150 = load %class.Tree * * %tmp123
  %tmp151 = call i1  @__SetHas_Right_Tree(%class.Tree * %tmp150, i1 true)
  store i1 %tmp151, i1 * %tmp122
  %tmp152 = load %class.Tree * * %tmp123
  %tmp153 = load %class.Tree * * %tmp121
  %tmp154 = call i1  @__SetRight_Tree(%class.Tree * %tmp152, %class.Tree * %tmp153)
  store i1 %tmp154, i1 * %tmp122
  br label %label17
label17:
  br label %label11
label11:
  br label %label6
label8:
  ret i1 true
}
define i1 @__Delete_Tree(%class.Tree * %this, i32 %v_key) {
  %tmp155 = alloca i32
  store i32 %v_key, i32 * %tmp155
  %tmp156 = alloca %class.Tree *
  %tmp157 = alloca %class.Tree *
  %tmp158 = alloca i1
  %tmp159 = alloca i1
  %tmp160 = alloca i1
  %tmp161 = alloca i1
  %tmp162 = alloca i32
  store %class.Tree * %this, %class.Tree * * %tmp156
  store %class.Tree * %this, %class.Tree * * %tmp157
  store i1 true, i1 * %tmp158
  store i1 false, i1 * %tmp159
  store i1 true, i1 * %tmp161
  br label %label18
label18:
  %tmp163 = load i1 * %tmp158
  br i1 %tmp163, label %label19, label %label20
label19:
  %tmp164 = load %class.Tree * * %tmp156
  %tmp165 = call i32  @__GetKey_Tree(%class.Tree * %tmp164)
  store i32 %tmp165, i32 * %tmp162
  %tmp166 = load i32 * %tmp155
  %tmp167 = load i32 * %tmp162
  %tmp168 = icmp slt i32 %tmp166, %tmp167
  br i1 %tmp168, label %label21, label %label22
label21:
  %tmp169 = load %class.Tree * * %tmp156
  %tmp170 = call i1  @__GetHas_Left_Tree(%class.Tree * %tmp169)
  br i1 %tmp170, label %label24, label %label25
label24:
  %tmp171 = load %class.Tree * * %tmp156
  store %class.Tree * %tmp171, %class.Tree * * %tmp157
  %tmp172 = load %class.Tree * * %tmp156
  %tmp173 = call %class.Tree *  @__GetLeft_Tree(%class.Tree * %tmp172)
  store %class.Tree * %tmp173, %class.Tree * * %tmp156
  br label %label26
label25:
  store i1 false, i1 * %tmp158
  br label %label26
label26:
  br label %label23
label22:
  %tmp174 = load i32 * %tmp162
  %tmp175 = load i32 * %tmp155
  %tmp176 = icmp slt i32 %tmp174, %tmp175
  br i1 %tmp176, label %label27, label %label28
label27:
  %tmp177 = load %class.Tree * * %tmp156
  %tmp178 = call i1  @__GetHas_Right_Tree(%class.Tree * %tmp177)
  br i1 %tmp178, label %label30, label %label31
label30:
  %tmp179 = load %class.Tree * * %tmp156
  store %class.Tree * %tmp179, %class.Tree * * %tmp157
  %tmp180 = load %class.Tree * * %tmp156
  %tmp181 = call %class.Tree *  @__GetRight_Tree(%class.Tree * %tmp180)
  store %class.Tree * %tmp181, %class.Tree * * %tmp156
  br label %label32
label31:
  store i1 false, i1 * %tmp158
  br label %label32
label32:
  br label %label29
label28:
  %tmp182 = load i1 * %tmp161
  br i1 %tmp182, label %label33, label %label34
label33:
  %tmp183 = load %class.Tree * * %tmp156
  %tmp184 = call i1  @__GetHas_Right_Tree(%class.Tree * %tmp183)
  %tmp185 = zext i1 %tmp184 to i32
  %tmp186 = add i32 %tmp185, 1
  %tmp187 = trunc i32 %tmp186 to i1
  %tmp188 = load %class.Tree * * %tmp156
  %tmp189 = call i1  @__GetHas_Left_Tree(%class.Tree * %tmp188)
  %tmp190 = zext i1 %tmp189 to i32
  %tmp191 = add i32 %tmp190, 1
  %tmp192 = trunc i32 %tmp191 to i1
  %tmp193 = zext i1 %tmp187 to i32
  %tmp194 = zext i1 %tmp192 to i32
  %tmp195 = mul i32 %tmp193, %tmp194
  %tmp196 = trunc i32 %tmp195 to i1
  br i1 %tmp196, label %label36, label %label37
label36:
  store i1 true, i1 * %tmp160
  br label %label38
label37:
  %tmp197 = load %class.Tree * * %tmp157
  %tmp198 = load %class.Tree * * %tmp156
  %tmp199 = call i1  @__Remove_Tree(%class.Tree * %this, %class.Tree * %tmp197, %class.Tree * %tmp198)
  store i1 %tmp199, i1 * %tmp160
  br label %label38
label38:
  br label %label35
label34:
  %tmp200 = load %class.Tree * * %tmp157
  %tmp201 = load %class.Tree * * %tmp156
  %tmp202 = call i1  @__Remove_Tree(%class.Tree * %this, %class.Tree * %tmp200, %class.Tree * %tmp201)
  store i1 %tmp202, i1 * %tmp160
  br label %label35
label35:
  store i1 true, i1 * %tmp159
  store i1 false, i1 * %tmp158
  br label %label29
label29:
  br label %label23
label23:
  store i1 false, i1 * %tmp161
  br label %label18
label20:
  %tmp203 = load i1 * %tmp159
  ret i1 %tmp203
}
define i1 @__Remove_Tree(%class.Tree * %this, %class.Tree * %p_node, %class.Tree * %c_node) {
  %tmp204 = alloca %class.Tree *
  store %class.Tree * %p_node, %class.Tree * * %tmp204
  %tmp205 = alloca %class.Tree *
  store %class.Tree * %c_node, %class.Tree * * %tmp205
  %tmp206 = alloca i1
  %tmp207 = alloca i32
  %tmp208 = alloca i32
  %tmp209 = load %class.Tree * * %tmp205
  %tmp210 = call i1  @__GetHas_Left_Tree(%class.Tree * %tmp209)
  br i1 %tmp210, label %label39, label %label40
label39:
  %tmp211 = load %class.Tree * * %tmp204
  %tmp212 = load %class.Tree * * %tmp205
  %tmp213 = call i1  @__RemoveLeft_Tree(%class.Tree * %this, %class.Tree * %tmp211, %class.Tree * %tmp212)
  store i1 %tmp213, i1 * %tmp206
  br label %label41
label40:
  %tmp214 = load %class.Tree * * %tmp205
  %tmp215 = call i1  @__GetHas_Right_Tree(%class.Tree * %tmp214)
  br i1 %tmp215, label %label42, label %label43
label42:
  %tmp216 = load %class.Tree * * %tmp204
  %tmp217 = load %class.Tree * * %tmp205
  %tmp218 = call i1  @__RemoveRight_Tree(%class.Tree * %this, %class.Tree * %tmp216, %class.Tree * %tmp217)
  store i1 %tmp218, i1 * %tmp206
  br label %label44
label43:
  %tmp219 = load %class.Tree * * %tmp205
  %tmp220 = call i32  @__GetKey_Tree(%class.Tree * %tmp219)
  store i32 %tmp220, i32 * %tmp207
  %tmp221 = load %class.Tree * * %tmp204
  %tmp222 = call %class.Tree *  @__GetLeft_Tree(%class.Tree * %tmp221)
  %tmp223 = call i32  @__GetKey_Tree(%class.Tree * %tmp222)
  store i32 %tmp223, i32 * %tmp208
  %tmp224 = load i32 * %tmp207
  %tmp225 = load i32 * %tmp208
  %tmp226 = call i1  @__Compare_Tree(%class.Tree * %this, i32 %tmp224, i32 %tmp225)
  br i1 %tmp226, label %label45, label %label46
label45:
  %tmp227 = load %class.Tree * * %tmp204
  %tmp228 = getelementptr %class.Tree * %this, i32 0, i32 5
  %tmp229 = load %class.Tree * * %tmp228
  %tmp230 = call i1  @__SetLeft_Tree(%class.Tree * %tmp227, %class.Tree * %tmp229)
  store i1 %tmp230, i1 * %tmp206
  %tmp231 = load %class.Tree * * %tmp204
  %tmp232 = call i1  @__SetHas_Left_Tree(%class.Tree * %tmp231, i1 false)
  store i1 %tmp232, i1 * %tmp206
  br label %label47
label46:
  %tmp233 = load %class.Tree * * %tmp204
  %tmp234 = getelementptr %class.Tree * %this, i32 0, i32 5
  %tmp235 = load %class.Tree * * %tmp234
  %tmp236 = call i1  @__SetRight_Tree(%class.Tree * %tmp233, %class.Tree * %tmp235)
  store i1 %tmp236, i1 * %tmp206
  %tmp237 = load %class.Tree * * %tmp204
  %tmp238 = call i1  @__SetHas_Right_Tree(%class.Tree * %tmp237, i1 false)
  store i1 %tmp238, i1 * %tmp206
  br label %label47
label47:
  br label %label44
label44:
  br label %label41
label41:
  ret i1 true
}
define i1 @__RemoveRight_Tree(%class.Tree * %this, %class.Tree * %p_node, %class.Tree * %c_node) {
  %tmp239 = alloca %class.Tree *
  store %class.Tree * %p_node, %class.Tree * * %tmp239
  %tmp240 = alloca %class.Tree *
  store %class.Tree * %c_node, %class.Tree * * %tmp240
  %tmp241 = alloca i1
  br label %label48
label48:
  %tmp242 = load %class.Tree * * %tmp240
  %tmp243 = call i1  @__GetHas_Right_Tree(%class.Tree * %tmp242)
  br i1 %tmp243, label %label49, label %label50
label49:
  %tmp244 = load %class.Tree * * %tmp240
  %tmp245 = load %class.Tree * * %tmp240
  %tmp246 = call %class.Tree *  @__GetRight_Tree(%class.Tree * %tmp245)
  %tmp247 = call i32  @__GetKey_Tree(%class.Tree * %tmp246)
  %tmp248 = call i1  @__SetKey_Tree(%class.Tree * %tmp244, i32 %tmp247)
  store i1 %tmp248, i1 * %tmp241
  %tmp249 = load %class.Tree * * %tmp240
  store %class.Tree * %tmp249, %class.Tree * * %tmp239
  %tmp250 = load %class.Tree * * %tmp240
  %tmp251 = call %class.Tree *  @__GetRight_Tree(%class.Tree * %tmp250)
  store %class.Tree * %tmp251, %class.Tree * * %tmp240
  br label %label48
label50:
  %tmp252 = load %class.Tree * * %tmp239
  %tmp253 = getelementptr %class.Tree * %this, i32 0, i32 5
  %tmp254 = load %class.Tree * * %tmp253
  %tmp255 = call i1  @__SetRight_Tree(%class.Tree * %tmp252, %class.Tree * %tmp254)
  store i1 %tmp255, i1 * %tmp241
  %tmp256 = load %class.Tree * * %tmp239
  %tmp257 = call i1  @__SetHas_Right_Tree(%class.Tree * %tmp256, i1 false)
  store i1 %tmp257, i1 * %tmp241
  ret i1 true
}
define i1 @__RemoveLeft_Tree(%class.Tree * %this, %class.Tree * %p_node, %class.Tree * %c_node) {
  %tmp258 = alloca %class.Tree *
  store %class.Tree * %p_node, %class.Tree * * %tmp258
  %tmp259 = alloca %class.Tree *
  store %class.Tree * %c_node, %class.Tree * * %tmp259
  %tmp260 = alloca i1
  br label %label51
label51:
  %tmp261 = load %class.Tree * * %tmp259
  %tmp262 = call i1  @__GetHas_Left_Tree(%class.Tree * %tmp261)
  br i1 %tmp262, label %label52, label %label53
label52:
  %tmp263 = load %class.Tree * * %tmp259
  %tmp264 = load %class.Tree * * %tmp259
  %tmp265 = call %class.Tree *  @__GetLeft_Tree(%class.Tree * %tmp264)
  %tmp266 = call i32  @__GetKey_Tree(%class.Tree * %tmp265)
  %tmp267 = call i1  @__SetKey_Tree(%class.Tree * %tmp263, i32 %tmp266)
  store i1 %tmp267, i1 * %tmp260
  %tmp268 = load %class.Tree * * %tmp259
  store %class.Tree * %tmp268, %class.Tree * * %tmp258
  %tmp269 = load %class.Tree * * %tmp259
  %tmp270 = call %class.Tree *  @__GetLeft_Tree(%class.Tree * %tmp269)
  store %class.Tree * %tmp270, %class.Tree * * %tmp259
  br label %label51
label53:
  %tmp271 = load %class.Tree * * %tmp258
  %tmp272 = getelementptr %class.Tree * %this, i32 0, i32 5
  %tmp273 = load %class.Tree * * %tmp272
  %tmp274 = call i1  @__SetLeft_Tree(%class.Tree * %tmp271, %class.Tree * %tmp273)
  store i1 %tmp274, i1 * %tmp260
  %tmp275 = load %class.Tree * * %tmp258
  %tmp276 = call i1  @__SetHas_Left_Tree(%class.Tree * %tmp275, i1 false)
  store i1 %tmp276, i1 * %tmp260
  ret i1 true
}
define i32 @__Search_Tree(%class.Tree * %this, i32 %v_key) {
  %tmp277 = alloca i32
  store i32 %v_key, i32 * %tmp277
  %tmp278 = alloca %class.Tree *
  %tmp279 = alloca i32
  %tmp280 = alloca i1
  %tmp281 = alloca i32
  store %class.Tree * %this, %class.Tree * * %tmp278
  store i1 true, i1 * %tmp280
  store i32 0, i32 * %tmp279
  br label %label54
label54:
  %tmp282 = load i1 * %tmp280
  br i1 %tmp282, label %label55, label %label56
label55:
  %tmp283 = load %class.Tree * * %tmp278
  %tmp284 = call i32  @__GetKey_Tree(%class.Tree * %tmp283)
  store i32 %tmp284, i32 * %tmp281
  %tmp285 = load i32 * %tmp277
  %tmp286 = load i32 * %tmp281
  %tmp287 = icmp slt i32 %tmp285, %tmp286
  br i1 %tmp287, label %label57, label %label58
label57:
  %tmp288 = load %class.Tree * * %tmp278
  %tmp289 = call i1  @__GetHas_Left_Tree(%class.Tree * %tmp288)
  br i1 %tmp289, label %label60, label %label61
label60:
  %tmp290 = load %class.Tree * * %tmp278
  %tmp291 = call %class.Tree *  @__GetLeft_Tree(%class.Tree * %tmp290)
  store %class.Tree * %tmp291, %class.Tree * * %tmp278
  br label %label62
label61:
  store i1 false, i1 * %tmp280
  br label %label62
label62:
  br label %label59
label58:
  %tmp292 = load i32 * %tmp281
  %tmp293 = load i32 * %tmp277
  %tmp294 = icmp slt i32 %tmp292, %tmp293
  br i1 %tmp294, label %label63, label %label64
label63:
  %tmp295 = load %class.Tree * * %tmp278
  %tmp296 = call i1  @__GetHas_Right_Tree(%class.Tree * %tmp295)
  br i1 %tmp296, label %label66, label %label67
label66:
  %tmp297 = load %class.Tree * * %tmp278
  %tmp298 = call %class.Tree *  @__GetRight_Tree(%class.Tree * %tmp297)
  store %class.Tree * %tmp298, %class.Tree * * %tmp278
  br label %label68
label67:
  store i1 false, i1 * %tmp280
  br label %label68
label68:
  br label %label65
label64:
  store i32 1, i32 * %tmp279
  store i1 false, i1 * %tmp280
  br label %label65
label65:
  br label %label59
label59:
  br label %label54
label56:
  %tmp299 = load i32 * %tmp279
  ret i32 %tmp299
}
define i1 @__Print_Tree(%class.Tree * %this) {
  %tmp300 = alloca i1
  %tmp301 = alloca %class.Tree *
  store %class.Tree * %this, %class.Tree * * %tmp301
  %tmp302 = load %class.Tree * * %tmp301
  %tmp303 = call i1  @__RecPrint_Tree(%class.Tree * %this, %class.Tree * %tmp302)
  store i1 %tmp303, i1 * %tmp300
  ret i1 true
}
define i1 @__RecPrint_Tree(%class.Tree * %this, %class.Tree * %node) {
  %tmp304 = alloca %class.Tree *
  store %class.Tree * %node, %class.Tree * * %tmp304
  %tmp305 = alloca i1
  %tmp306 = load %class.Tree * * %tmp304
  %tmp307 = call i1  @__GetHas_Left_Tree(%class.Tree * %tmp306)
  br i1 %tmp307, label %label69, label %label70
label69:
  %tmp308 = load %class.Tree * * %tmp304
  %tmp309 = call %class.Tree *  @__GetLeft_Tree(%class.Tree * %tmp308)
  %tmp310 = call i1  @__RecPrint_Tree(%class.Tree * %this, %class.Tree * %tmp309)
  store i1 %tmp310, i1 * %tmp305
  br label %label71
label70:
  store i1 true, i1 * %tmp305
  br label %label71
label71:
  %tmp311 = load %class.Tree * * %tmp304
  %tmp312 = call i32  @__GetKey_Tree(%class.Tree * %tmp311)
  %tmp313 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp314 = call i32 (i8 *, ...)* @printf(i8 * %tmp313, i32 %tmp312)
  %tmp315 = load %class.Tree * * %tmp304
  %tmp316 = call i1  @__GetHas_Right_Tree(%class.Tree * %tmp315)
  br i1 %tmp316, label %label72, label %label73
label72:
  %tmp317 = load %class.Tree * * %tmp304
  %tmp318 = call %class.Tree *  @__GetRight_Tree(%class.Tree * %tmp317)
  %tmp319 = call i1  @__RecPrint_Tree(%class.Tree * %this, %class.Tree * %tmp318)
  store i1 %tmp319, i1 * %tmp305
  br label %label74
label73:
  store i1 true, i1 * %tmp305
  br label %label74
label74:
  ret i1 true
}
define i32 @__accept_Tree(%class.Tree * %this, %class.Visitor * %v) {
  %tmp320 = alloca %class.Visitor *
  store %class.Visitor * %v, %class.Visitor * * %tmp320
  %tmp321 = alloca i32
  %tmp322 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp323 = call i32 (i8 *, ...)* @printf(i8 * %tmp322, i32 333)
  %tmp324 = load %class.Visitor * * %tmp320
  %tmp325 = call i32  @__visit_Visitor(%class.Visitor * %tmp324, %class.Tree * %this)
  store i32 %tmp325, i32 * %tmp321
  ret i32 0
}
%class.Visitor = type { %class.Tree *, %class.Tree * }
define i32 @__visit_Visitor(%class.Visitor * %this, %class.Tree * %n) {
  %tmp326 = alloca %class.Tree *
  store %class.Tree * %n, %class.Tree * * %tmp326
  %tmp327 = alloca i32
  %tmp328 = load %class.Tree * * %tmp326
  %tmp329 = call i1  @__GetHas_Right_Tree(%class.Tree * %tmp328)
  br i1 %tmp329, label %label75, label %label76
label75:
  %tmp330 = getelementptr %class.Visitor * %this, i32 0, i32 1
  %tmp331 = load %class.Tree * * %tmp326
  %tmp332 = call %class.Tree *  @__GetRight_Tree(%class.Tree * %tmp331)
  store %class.Tree * %tmp332, %class.Tree * * %tmp330
  %tmp333 = getelementptr %class.Visitor * %this, i32 0, i32 1
  %tmp334 = load %class.Tree * * %tmp333
  %tmp335 = call i32  @__accept_Tree(%class.Tree * %tmp334, %class.Visitor * %this)
  store i32 %tmp335, i32 * %tmp327
  br label %label77
label76:
  store i32 0, i32 * %tmp327
  br label %label77
label77:
  %tmp336 = load %class.Tree * * %tmp326
  %tmp337 = call i1  @__GetHas_Left_Tree(%class.Tree * %tmp336)
  br i1 %tmp337, label %label78, label %label79
label78:
  %tmp338 = getelementptr %class.Visitor * %this, i32 0, i32 0
  %tmp339 = load %class.Tree * * %tmp326
  %tmp340 = call %class.Tree *  @__GetLeft_Tree(%class.Tree * %tmp339)
  store %class.Tree * %tmp340, %class.Tree * * %tmp338
  %tmp341 = getelementptr %class.Visitor * %this, i32 0, i32 0
  %tmp342 = load %class.Tree * * %tmp341
  %tmp343 = call i32  @__accept_Tree(%class.Tree * %tmp342, %class.Visitor * %this)
  store i32 %tmp343, i32 * %tmp327
  br label %label80
label79:
  store i32 0, i32 * %tmp327
  br label %label80
label80:
  ret i32 0
}
%class.MyVisitor = type { %class.Tree *, %class.Tree * }
define i32 @__visit_MyVisitor(%class.MyVisitor * %this, %class.Tree * %n) {
  %tmp344 = alloca %class.Tree *
  store %class.Tree * %n, %class.Tree * * %tmp344
  %tmp345 = alloca i32
  %tmp346 = load %class.Tree * * %tmp344
  %tmp347 = call i1  @__GetHas_Right_Tree(%class.Tree * %tmp346)
  br i1 %tmp347, label %label81, label %label82
label81:
  %tmp348 = getelementptr %class.MyVisitor * %this, i32 0, i32 0
  %tmp349 = load %class.Tree * * %tmp344
  %tmp350 = call %class.Tree *  @__GetRight_Tree(%class.Tree * %tmp349)
  store %class.Tree * %tmp350, %class.Tree * * %tmp348
  %tmp351 = getelementptr %class.MyVisitor * %this, i32 0, i32 0
  %tmp352 = load %class.Tree * * %tmp351
  %tmp353 = call i32  @__accept_Tree(%class.Tree * %tmp352, %class.MyVisitor * %this)
  store i32 %tmp353, i32 * %tmp345
  br label %label83
label82:
  store i32 0, i32 * %tmp345
  br label %label83
label83:
  %tmp354 = load %class.Tree * * %tmp344
  %tmp355 = call i32  @__GetKey_Tree(%class.Tree * %tmp354)
  %tmp356 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp357 = call i32 (i8 *, ...)* @printf(i8 * %tmp356, i32 %tmp355)
  %tmp358 = load %class.Tree * * %tmp344
  %tmp359 = call i1  @__GetHas_Left_Tree(%class.Tree * %tmp358)
  br i1 %tmp359, label %label84, label %label85
label84:
  %tmp360 = getelementptr %class.MyVisitor * %this, i32 0, i32 1
  %tmp361 = load %class.Tree * * %tmp344
  %tmp362 = call %class.Tree *  @__GetLeft_Tree(%class.Tree * %tmp361)
  store %class.Tree * %tmp362, %class.Tree * * %tmp360
  %tmp363 = getelementptr %class.MyVisitor * %this, i32 0, i32 1
  %tmp364 = load %class.Tree * * %tmp363
  %tmp365 = call i32  @__accept_Tree(%class.Tree * %tmp364, %class.MyVisitor * %this)
  store i32 %tmp365, i32 * %tmp345
  br label %label86
label85:
  store i32 0, i32 * %tmp345
  br label %label86
label86:
  ret i32 0
}
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
