@.formatting.string = private constant [4 x i8] c"%d\0A\00"
define i32 @main() {
entry:
  %tmp0 = alloca i32
  store i32 0, i32 * %tmp0
  %tmp1 = call i8 *  @malloc(i32 12)
  %tmp2 = bitcast i8 * %tmp1 to %class.QS *
  %tmp3 = call i32  @__Start_QS(%class.QS * %tmp2, i32 10)
  %tmp4 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp5 = call i32 (i8 *, ...)* @printf(i8 * %tmp4, i32 %tmp3)
  %tmp6 = load i32 * %tmp0
  ret i32 %tmp6
}
%class.QS = type { i32 *, i32 }
define i32 @__Start_QS(%class.QS * %this, i32 %sz) {
  %tmp7 = alloca i32
  store i32 %sz, i32 * %tmp7
  %tmp8 = alloca i32
  %tmp9 = load i32 * %tmp7
  %tmp10 = call i32  @__Init_QS(%class.QS * %this, i32 %tmp9)
  store i32 %tmp10, i32 * %tmp8
  %tmp11 = call i32  @__Print_QS(%class.QS * %this)
  store i32 %tmp11, i32 * %tmp8
  %tmp12 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp13 = call i32 (i8 *, ...)* @printf(i8 * %tmp12, i32 9999)
  %tmp14 = getelementptr %class.QS * %this, i32 0, i32 1
  %tmp15 = load i32 * %tmp14
  %tmp16 = sub i32 %tmp15, 1
  store i32 %tmp16, i32 * %tmp8
  %tmp17 = load i32 * %tmp8
  %tmp18 = call i32  @__Sort_QS(%class.QS * %this, i32 0, i32 %tmp17)
  store i32 %tmp18, i32 * %tmp8
  %tmp19 = call i32  @__Print_QS(%class.QS * %this)
  store i32 %tmp19, i32 * %tmp8
  ret i32 0
}
define i32 @__Sort_QS(%class.QS * %this, i32 %left, i32 %right) {
  %tmp20 = alloca i32
  store i32 %left, i32 * %tmp20
  %tmp21 = alloca i32
  store i32 %right, i32 * %tmp21
  %tmp22 = alloca i32
  %tmp23 = alloca i32
  %tmp24 = alloca i32
  %tmp25 = alloca i32
  %tmp26 = alloca i32
  %tmp27 = alloca i1
  %tmp28 = alloca i1
  %tmp29 = alloca i32
  store i32 0, i32 * %tmp26
  %tmp30 = load i32 * %tmp20
  %tmp31 = load i32 * %tmp21
  %tmp32 = icmp slt i32 %tmp30, %tmp31
  br i1 %tmp32, label %label0, label %label1
label0:
  %tmp33 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp34 = load i32 * * %tmp33
  %tmp37 = load i32 * %tmp21
  %tmp36 = mul i32 %tmp37, 1
  %tmp38 = add i32 %tmp36, 1
  %tmp35 = getelementptr i32 * %tmp34, i32 %tmp38
  %tmp39 = load i32 * %tmp35
  store i32 %tmp39, i32 * %tmp22
  %tmp40 = load i32 * %tmp20
  %tmp41 = sub i32 %tmp40, 1
  store i32 %tmp41, i32 * %tmp23
  %tmp42 = load i32 * %tmp21
  store i32 %tmp42, i32 * %tmp24
  store i1 true, i1 * %tmp27
  br label %label3
label3:
  %tmp43 = load i1 * %tmp27
  br i1 %tmp43, label %label4, label %label5
label4:
  store i1 true, i1 * %tmp28
  br label %label6
label6:
  %tmp44 = load i1 * %tmp28
  br i1 %tmp44, label %label7, label %label8
label7:
  %tmp45 = load i32 * %tmp23
  %tmp46 = add i32 %tmp45, 1
  store i32 %tmp46, i32 * %tmp23
  %tmp47 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp48 = load i32 * * %tmp47
  %tmp51 = load i32 * %tmp23
  %tmp50 = mul i32 %tmp51, 1
  %tmp52 = add i32 %tmp50, 1
  %tmp49 = getelementptr i32 * %tmp48, i32 %tmp52
  %tmp53 = load i32 * %tmp49
  store i32 %tmp53, i32 * %tmp29
  %tmp54 = load i32 * %tmp29
  %tmp55 = load i32 * %tmp22
  %tmp56 = icmp slt i32 %tmp54, %tmp55
  %tmp57 = zext i1 %tmp56 to i32
  %tmp58 = add i32 %tmp57, 1
  %tmp59 = trunc i32 %tmp58 to i1
  br i1 %tmp59, label %label9, label %label10
label9:
  store i1 false, i1 * %tmp28
  br label %label11
label10:
  store i1 true, i1 * %tmp28
  br label %label11
label11:
  br label %label6
label8:
  store i1 true, i1 * %tmp28
  br label %label12
label12:
  %tmp60 = load i1 * %tmp28
  br i1 %tmp60, label %label13, label %label14
label13:
  %tmp61 = load i32 * %tmp24
  %tmp62 = sub i32 %tmp61, 1
  store i32 %tmp62, i32 * %tmp24
  %tmp63 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp64 = load i32 * * %tmp63
  %tmp67 = load i32 * %tmp24
  %tmp66 = mul i32 %tmp67, 1
  %tmp68 = add i32 %tmp66, 1
  %tmp65 = getelementptr i32 * %tmp64, i32 %tmp68
  %tmp69 = load i32 * %tmp65
  store i32 %tmp69, i32 * %tmp29
  %tmp70 = load i32 * %tmp22
  %tmp71 = load i32 * %tmp29
  %tmp72 = icmp slt i32 %tmp70, %tmp71
  %tmp73 = zext i1 %tmp72 to i32
  %tmp74 = add i32 %tmp73, 1
  %tmp75 = trunc i32 %tmp74 to i1
  br i1 %tmp75, label %label15, label %label16
label15:
  store i1 false, i1 * %tmp28
  br label %label17
label16:
  store i1 true, i1 * %tmp28
  br label %label17
label17:
  br label %label12
label14:
  %tmp76 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp77 = load i32 * * %tmp76
  %tmp80 = load i32 * %tmp23
  %tmp79 = mul i32 %tmp80, 1
  %tmp81 = add i32 %tmp79, 1
  %tmp78 = getelementptr i32 * %tmp77, i32 %tmp81
  %tmp82 = load i32 * %tmp78
  store i32 %tmp82, i32 * %tmp26
  %tmp83 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp84 = load i32 * * %tmp83
  %tmp87 = load i32 * %tmp23
  %tmp86 = mul i32 %tmp87, 1
  %tmp88 = add i32 %tmp86, 1
  %tmp85 = getelementptr i32 * %tmp84, i32 %tmp88
  %tmp89 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp90 = load i32 * * %tmp89
  %tmp93 = load i32 * %tmp24
  %tmp92 = mul i32 %tmp93, 1
  %tmp94 = add i32 %tmp92, 1
  %tmp91 = getelementptr i32 * %tmp90, i32 %tmp94
  %tmp95 = load i32 * %tmp91
  store i32 %tmp95, i32 * %tmp85
  %tmp96 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp97 = load i32 * * %tmp96
  %tmp100 = load i32 * %tmp24
  %tmp99 = mul i32 %tmp100, 1
  %tmp101 = add i32 %tmp99, 1
  %tmp98 = getelementptr i32 * %tmp97, i32 %tmp101
  %tmp102 = load i32 * %tmp26
  store i32 %tmp102, i32 * %tmp98
  %tmp103 = load i32 * %tmp24
  %tmp104 = load i32 * %tmp23
  %tmp105 = add i32 %tmp104, 1
  %tmp106 = icmp slt i32 %tmp103, %tmp105
  br i1 %tmp106, label %label18, label %label19
label18:
  store i1 false, i1 * %tmp27
  br label %label20
label19:
  store i1 true, i1 * %tmp27
  br label %label20
label20:
  br label %label3
label5:
  %tmp107 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp108 = load i32 * * %tmp107
  %tmp111 = load i32 * %tmp24
  %tmp110 = mul i32 %tmp111, 1
  %tmp112 = add i32 %tmp110, 1
  %tmp109 = getelementptr i32 * %tmp108, i32 %tmp112
  %tmp113 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp114 = load i32 * * %tmp113
  %tmp117 = load i32 * %tmp23
  %tmp116 = mul i32 %tmp117, 1
  %tmp118 = add i32 %tmp116, 1
  %tmp115 = getelementptr i32 * %tmp114, i32 %tmp118
  %tmp119 = load i32 * %tmp115
  store i32 %tmp119, i32 * %tmp109
  %tmp120 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp121 = load i32 * * %tmp120
  %tmp124 = load i32 * %tmp23
  %tmp123 = mul i32 %tmp124, 1
  %tmp125 = add i32 %tmp123, 1
  %tmp122 = getelementptr i32 * %tmp121, i32 %tmp125
  %tmp126 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp127 = load i32 * * %tmp126
  %tmp130 = load i32 * %tmp21
  %tmp129 = mul i32 %tmp130, 1
  %tmp131 = add i32 %tmp129, 1
  %tmp128 = getelementptr i32 * %tmp127, i32 %tmp131
  %tmp132 = load i32 * %tmp128
  store i32 %tmp132, i32 * %tmp122
  %tmp133 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp134 = load i32 * * %tmp133
  %tmp137 = load i32 * %tmp21
  %tmp136 = mul i32 %tmp137, 1
  %tmp138 = add i32 %tmp136, 1
  %tmp135 = getelementptr i32 * %tmp134, i32 %tmp138
  %tmp139 = load i32 * %tmp26
  store i32 %tmp139, i32 * %tmp135
  %tmp140 = load i32 * %tmp20
  %tmp141 = load i32 * %tmp23
  %tmp142 = sub i32 %tmp141, 1
  %tmp143 = call i32  @__Sort_QS(%class.QS * %this, i32 %tmp140, i32 %tmp142)
  store i32 %tmp143, i32 * %tmp25
  %tmp144 = load i32 * %tmp23
  %tmp145 = add i32 %tmp144, 1
  %tmp146 = load i32 * %tmp21
  %tmp147 = call i32  @__Sort_QS(%class.QS * %this, i32 %tmp145, i32 %tmp146)
  store i32 %tmp147, i32 * %tmp25
  br label %label2
label1:
  store i32 0, i32 * %tmp25
  br label %label2
label2:
  ret i32 0
}
define i32 @__Print_QS(%class.QS * %this) {
  %tmp148 = alloca i32
  store i32 0, i32 * %tmp148
  br label %label21
label21:
  %tmp149 = load i32 * %tmp148
  %tmp150 = getelementptr %class.QS * %this, i32 0, i32 1
  %tmp151 = load i32 * %tmp150
  %tmp152 = icmp slt i32 %tmp149, %tmp151
  br i1 %tmp152, label %label22, label %label23
label22:
  %tmp153 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp154 = load i32 * * %tmp153
  %tmp157 = load i32 * %tmp148
  %tmp156 = mul i32 %tmp157, 1
  %tmp158 = add i32 %tmp156, 1
  %tmp155 = getelementptr i32 * %tmp154, i32 %tmp158
  %tmp159 = load i32 * %tmp155
  %tmp160 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp161 = call i32 (i8 *, ...)* @printf(i8 * %tmp160, i32 %tmp159)
  %tmp162 = load i32 * %tmp148
  %tmp163 = add i32 %tmp162, 1
  store i32 %tmp163, i32 * %tmp148
  br label %label21
label23:
  ret i32 0
}
define i32 @__Init_QS(%class.QS * %this, i32 %sz) {
  %tmp164 = alloca i32
  store i32 %sz, i32 * %tmp164
  %tmp165 = getelementptr %class.QS * %this, i32 0, i32 1
  %tmp166 = load i32 * %tmp164
  store i32 %tmp166, i32 * %tmp165
  %tmp167 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp168 = load i32 * %tmp164
  %tmp169 = add i32 %tmp168, 1
  %tmp171 = mul i32 4, %tmp169
  %tmp172 = call i8* @malloc ( i32 %tmp171)
  %tmp170 = bitcast i8* %tmp172 to i32*
  store i32 %tmp168, i32 * %tmp170
  store i32 * %tmp170, i32 * * %tmp167
  %tmp173 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp174 = load i32 * * %tmp173
  %tmp176 = mul i32 0, 1
  %tmp177 = add i32 %tmp176, 1
  %tmp175 = getelementptr i32 * %tmp174, i32 %tmp177
  store i32 20, i32 * %tmp175
  %tmp178 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp179 = load i32 * * %tmp178
  %tmp181 = mul i32 1, 1
  %tmp182 = add i32 %tmp181, 1
  %tmp180 = getelementptr i32 * %tmp179, i32 %tmp182
  store i32 7, i32 * %tmp180
  %tmp183 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp184 = load i32 * * %tmp183
  %tmp186 = mul i32 2, 1
  %tmp187 = add i32 %tmp186, 1
  %tmp185 = getelementptr i32 * %tmp184, i32 %tmp187
  store i32 12, i32 * %tmp185
  %tmp188 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp189 = load i32 * * %tmp188
  %tmp191 = mul i32 3, 1
  %tmp192 = add i32 %tmp191, 1
  %tmp190 = getelementptr i32 * %tmp189, i32 %tmp192
  store i32 18, i32 * %tmp190
  %tmp193 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp194 = load i32 * * %tmp193
  %tmp196 = mul i32 4, 1
  %tmp197 = add i32 %tmp196, 1
  %tmp195 = getelementptr i32 * %tmp194, i32 %tmp197
  store i32 2, i32 * %tmp195
  %tmp198 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp199 = load i32 * * %tmp198
  %tmp201 = mul i32 5, 1
  %tmp202 = add i32 %tmp201, 1
  %tmp200 = getelementptr i32 * %tmp199, i32 %tmp202
  store i32 11, i32 * %tmp200
  %tmp203 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp204 = load i32 * * %tmp203
  %tmp206 = mul i32 6, 1
  %tmp207 = add i32 %tmp206, 1
  %tmp205 = getelementptr i32 * %tmp204, i32 %tmp207
  store i32 6, i32 * %tmp205
  %tmp208 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp209 = load i32 * * %tmp208
  %tmp211 = mul i32 7, 1
  %tmp212 = add i32 %tmp211, 1
  %tmp210 = getelementptr i32 * %tmp209, i32 %tmp212
  store i32 9, i32 * %tmp210
  %tmp213 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp214 = load i32 * * %tmp213
  %tmp216 = mul i32 8, 1
  %tmp217 = add i32 %tmp216, 1
  %tmp215 = getelementptr i32 * %tmp214, i32 %tmp217
  store i32 19, i32 * %tmp215
  %tmp218 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp219 = load i32 * * %tmp218
  %tmp221 = mul i32 9, 1
  %tmp222 = add i32 %tmp221, 1
  %tmp220 = getelementptr i32 * %tmp219, i32 %tmp222
  store i32 5, i32 * %tmp220
  ret i32 0
}
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
