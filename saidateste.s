@.formatting.string = private constant [4 x i8] c"%d\0A\00"
define i32 @main() {
entry:
  %tmp0 = alloca i32
  store i32 0, i32 * %tmp0
  %tmp1 = call i8 *  @malloc(i32 0)
  %tmp2 = bitcast i8 * %tmp1 to %class.Fac *
  %tmp3 = call i32  @__ComputeFac_Fac(%class.Fac * %tmp2, i32 10)
  %tmp4 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp5 = call i32 (i8 *, ...)* @printf(i8 * %tmp4, i32 %tmp3)
  %tmp6 = load i32 * %tmp0
  ret i32 %tmp6
}
%class.Fac = type { }
define i32 @__ComputeFac_Fac(%class.Fac * %this, i32 num) {
  %tmp7 = alloca i32
  store i32 num, i32 * %tmp7
  %tmp8 = alloca i32
  %tmp9 = load i32 * %tmp7
  %tmp10 = icmp slt i32 %tmp9, 1
  br i1 %tmp10, label %label0, label %label1
label0:
  store i32 1, i32 * %tmp8
  br label %label2
label1:
  %tmp11 = load i32 * %tmp7
  %tmp12 = load i32 * %tmp7
  %tmp13 = sub i32 %tmp12, 1
  %tmp14 = call i32  @__ComputeFac_Fac(%class.Fac * Fac, i32 %tmp13)
  %tmp15 = mul i32 %tmp11, %tmp14
  store i32 %tmp15, i32 * %tmp8
  br label %label2
label2:
  %tmp16 = load i32 * %tmp8
  ret i32 %tmp16
}
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
