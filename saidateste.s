@.formatting.string = private constant [4 x i8] c"%d\0A\00"
define i32 @main() {
entry:
  %tmp0 = alloca i32
  store i32 0, i32 * %tmp0
  %tmp1 = call i8 *  @malloc(i32 0)
  %tmp2 = bitcast i8 * %tmp1 to %class.a *
  %tmp3 = call i32  @__i_a(%class.a * %tmp2)
  %tmp4 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp5 = call i32 (i8 *, ...)* @printf(i8 * %tmp4, i32 %tmp3)
  %tmp6 = load i32 * %tmp0
  ret i32 %tmp6
}
%class.a = type { }
define %class.a * @__A_a(%class.a * %this) {
  ret %class.a * %this
}
define i32 @__i_a(%class.a * %this) {
  ret i32 0
}
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
