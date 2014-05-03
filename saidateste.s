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
  %tmp38 = load i32 * %tmp21
  %tmp37 = add i32 * %tmp38, 1
  %tmp35 = mul i32 * %tmp37, 4
  %tmp36 = add i32 * %tmp34, %tmp35
  %tmp39 = load i32 * %tmp36
  store i32 %tmp39, i32 * %tmp22
  %tmp40 = load i32 * %tmp20
  %tmp41 = sub i32 %tmp40, 1
  store i32 %tmp41, i32 * %tmp23
  %tmp42 = load i32 * %tmp21
  store i32 %tmp42, i32 * %tmp24
  store i1 true, i1 * %tmp27
label3:
  %tmp43 = load i1 * %tmp27
  br i1 %tmp43, label %label4, label %label5
label4:
  store i1 true, i1 * %tmp28
label6:
  %tmp44 = load i1 * %tmp28
  br i1 %tmp44, label %label7, label %label8
label7:
  %tmp45 = load i32 * %tmp23
  %tmp46 = add i32 %tmp45, 1
  store i32 %tmp46, i32 * %tmp23
  %tmp47 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp48 = load i32 * * %tmp47
  %tmp52 = load i32 * %tmp23
  %tmp51 = add i32 * %tmp52, 1
  %tmp49 = mul i32 * %tmp51, 4
  %tmp50 = add i32 * %tmp48, %tmp49
  %tmp53 = load i32 * %tmp50
  store i32 %tmp53, i32 * %tmp29
  %tmp54 = load i32 * %tmp29
  %tmp55 = load i32 * %tmp22
  %tmp56 = icmp slt i32 %tmp54, %tmp55
  %tmp57 = mul i32 %tmp56, %tmp56
  br i1 %tmp57, label %label9, label %label10
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
label12:
  %tmp58 = load i1 * %tmp28
  br i1 %tmp58, label %label13, label %label14
label13:
  %tmp59 = load i32 * %tmp24
  %tmp60 = sub i32 %tmp59, 1
  store i32 %tmp60, i32 * %tmp24
  %tmp61 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp62 = load i32 * * %tmp61
  %tmp66 = load i32 * %tmp24
  %tmp65 = add i32 * %tmp66, 1
  %tmp63 = mul i32 * %tmp65, 4
  %tmp64 = add i32 * %tmp62, %tmp63
  %tmp67 = load i32 * %tmp64
  store i32 %tmp67, i32 * %tmp29
  %tmp68 = load i32 * %tmp22
  %tmp69 = load i32 * %tmp29
  %tmp70 = icmp slt i32 %tmp68, %tmp69
  %tmp71 = mul i32 %tmp70, %tmp70
  br i1 %tmp71, label %label15, label %label16
label15:
  store i1 false, i1 * %tmp28
  br label %label17
label16:
  store i1 true, i1 * %tmp28
  br label %label17
label17:
  br label %label12
label14:
  %tmp72 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp73 = load i32 * * %tmp72
  %tmp77 = load i32 * %tmp23
  %tmp76 = add i32 * %tmp77, 1
  %tmp74 = mul i32 * %tmp76, 4
  %tmp75 = add i32 * %tmp73, %tmp74
  %tmp78 = load i32 * %tmp75
  store i32 %tmp78, i32 * %tmp26
  %tmp79 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp83 = load i32 * %tmp23
  %tmp82 = add i32 * %tmp83, 1
  %tmp84 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp85 = load i32 * * %tmp84
  %tmp89 = load i32 * %tmp24
  %tmp88 = add i32 * %tmp89, 1
  %tmp86 = mul i32 * %tmp88, 4
  %tmp87 = add i32 * %tmp85, %tmp86
  %tmp90 = load i32 * %tmp87
  store i32 %tmp90, i32 * %tmp81
  %tmp91 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp95 = load i32 * %tmp24
  %tmp94 = add i32 * %tmp95, 1
  %tmp96 = load i32 * %tmp26
  store i32 %tmp96, i32 * %tmp93
  %tmp97 = load i32 * %tmp24
  %tmp98 = load i32 * %tmp23
  %tmp99 = add i32 %tmp98, 1
  %tmp100 = icmp slt i32 %tmp97, %tmp99
  br i1 %tmp100, label %label18, label %label19
label18:
  store i1 false, i1 * %tmp27
  br label %label20
label19:
  store i1 true, i1 * %tmp27
  br label %label20
label20:
  br label %label3
label5:
  %tmp101 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp105 = load i32 * %tmp24
  %tmp104 = add i32 * %tmp105, 1
  %tmp106 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp107 = load i32 * * %tmp106
  %tmp111 = load i32 * %tmp23
  %tmp110 = add i32 * %tmp111, 1
  %tmp108 = mul i32 * %tmp110, 4
  %tmp109 = add i32 * %tmp107, %tmp108
  %tmp112 = load i32 * %tmp109
  store i32 %tmp112, i32 * %tmp103
  %tmp113 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp117 = load i32 * %tmp23
  %tmp116 = add i32 * %tmp117, 1
  %tmp118 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp119 = load i32 * * %tmp118
  %tmp123 = load i32 * %tmp21
  %tmp122 = add i32 * %tmp123, 1
  %tmp120 = mul i32 * %tmp122, 4
  %tmp121 = add i32 * %tmp119, %tmp120
  %tmp124 = load i32 * %tmp121
  store i32 %tmp124, i32 * %tmp115
  %tmp125 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp129 = load i32 * %tmp21
  %tmp128 = add i32 * %tmp129, 1
  %tmp130 = load i32 * %tmp26
  store i32 %tmp130, i32 * %tmp127
  %tmp131 = load i32 * %tmp20
  %tmp132 = load i32 * %tmp23
  %tmp133 = sub i32 %tmp132, 1
  %tmp134 = call i32  @__Sort_QS(%class.QS * %this, i32 %tmp131, i32 %tmp133)
  store i32 %tmp134, i32 * %tmp25
  %tmp135 = load i32 * %tmp23
  %tmp136 = add i32 %tmp135, 1
  %tmp137 = load i32 * %tmp21
  %tmp138 = call i32  @__Sort_QS(%class.QS * %this, i32 %tmp136, i32 %tmp137)
  store i32 %tmp138, i32 * %tmp25
  br label %label2
label1:
  store i32 0, i32 * %tmp25
  br label %label2
label2:
  ret i32 0
}
define i32 @__Print_QS(%class.QS * %this) {
  %tmp139 = alloca i32
  store i32 0, i32 * %tmp139
label21:
  %tmp140 = load i32 * %tmp139
  %tmp141 = getelementptr %class.QS * %this, i32 0, i32 1
  %tmp142 = load i32 * %tmp141
  %tmp143 = icmp slt i32 %tmp140, %tmp142
  br i1 %tmp143, label %label22, label %label23
label22:
  %tmp144 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp145 = load i32 * * %tmp144
  %tmp149 = load i32 * %tmp139
  %tmp148 = add i32 * %tmp149, 1
  %tmp146 = mul i32 * %tmp148, 4
  %tmp147 = add i32 * %tmp145, %tmp146
  %tmp150 = load i32 * %tmp147
  %tmp151 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp152 = call i32 (i8 *, ...)* @printf(i8 * %tmp151, i32 %tmp150)
  %tmp153 = load i32 * %tmp139
  %tmp154 = add i32 %tmp153, 1
  store i32 %tmp154, i32 * %tmp139
  br label %label21
label23:
  ret i32 0
}
define i32 @__Init_QS(%class.QS * %this, i32 %sz) {
  %tmp155 = alloca i32
  store i32 %sz, i32 * %tmp155
  %tmp156 = getelementptr %class.QS * %this, i32 0, i32 1
  %tmp157 = load i32 * %tmp155
  store i32 %tmp157, i32 * %tmp156
  %tmp158 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp159 = load i32 * %tmp155
  %tmp160 = add i32 %tmp159, 1
  %tmp162 = mul i32 4, %tmp160
  %tmp163 = call i8* @malloc ( i32 %tmp162)
  %tmp161 = bitcast i8* %tmp163 to i32*
  store i32 %tmp159, i32 * %tmp161
  store i32 * %tmp161, i32 * * %tmp158
  %tmp164 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp167 = add i32 * 0, 1
  store i32 20, i32 * %tmp166
  %tmp168 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp171 = add i32 * 1, 1
  store i32 7, i32 * %tmp170
  %tmp172 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp175 = add i32 * 2, 1
  store i32 12, i32 * %tmp174
  %tmp176 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp179 = add i32 * 3, 1
  store i32 18, i32 * %tmp178
  %tmp180 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp183 = add i32 * 4, 1
  store i32 2, i32 * %tmp182
  %tmp184 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp187 = add i32 * 5, 1
  store i32 11, i32 * %tmp186
  %tmp188 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp191 = add i32 * 6, 1
  store i32 6, i32 * %tmp190
  %tmp192 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp195 = add i32 * 7, 1
  store i32 9, i32 * %tmp194
  %tmp196 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp199 = add i32 * 8, 1
  store i32 19, i32 * %tmp198
  %tmp200 = getelementptr %class.QS * %this, i32 0, i32 0
  %tmp203 = add i32 * 9, 1
  store i32 5, i32 * %tmp202

  %tmp1 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp2 = call i32 (i8 *, ...)* @printf(i8 * %tmp1, i32 10)
  %tmp3 = load i32 * %tmp0
  ret i32 %tmp3
}
%class.Fac = type { }
define i32 @__ComputeFac_Fac(%class.Fac * %this) {
  %tmp4 = alloca i32 *
  %tmp5 = add i32 10, 1
  %tmp7 = mul i32 4, %tmp5
  %tmp8 = call i8* @malloc ( i32 %tmp7)
  %tmp6 = bitcast i8* %tmp8 to i32*
  store i32 10, i32 * %tmp6
  store i32 * %tmp6, i32 * * %tmp4
  %tmp11 = add i32 5, 1
  %tmp9 = mul i32 %tmp11, 4
  %tmp10 = add i32 %tmp4, %tmp9
  store i32 8, i32 * %tmp10

  ret i32 0
}
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
