@.formatting.string = private constant [4 x i8] c"%d\0A\00"
define i32 @main() {
entry:
  %tmp0 = alloca i32
  store i32 0, i32 * %tmp0
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
  %tmp9 = load i32 * * %tmp4
  %tmp12 = add i32 5, 1
  %tmp13 = inttoptr i32 %tmp12 to i32 *
  %tmp14 = mul i32 1, 4
  %tmp15 = inttoptr i32 %tmp14 to i32 *
  %tmp10 = mul i32 * %tmp13, %tmp15
  %tmp11 = add i32 * %tmp9, %tmp10
  store i32 8, i32 * %tmp11
  ret i32 0
}
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
