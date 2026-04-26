package kotlin.p003io;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FileTreeWalk.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0007"}, m535d2 = {"walk", "Lkotlin/io/FileTreeWalk;", "Ljava/io/File;", "direction", "Lkotlin/io/FileWalkDirection;", "walkBottomUp", "walkTopDown", "kotlin-stdlib"}, m536k = 5, m537mv = {1, 1, 16}, m539xi = 1, m540xs = "kotlin/io/FilesKt")
class FilesKt__FileTreeWalkKt extends FilesKt__FileReadWriteKt {
    public static /* synthetic */ FileTreeWalk walk$default(File file, FileWalkDirection fileWalkDirection, int i, Object obj) {
        if ((i & 1) != 0) {
            fileWalkDirection = FileWalkDirection.TOP_DOWN;
        }
        return FilesKt.walk(file, fileWalkDirection);
    }

    public static final FileTreeWalk walk(File walk, FileWalkDirection direction) {
        Intrinsics.checkParameterIsNotNull(walk, "$this$walk");
        Intrinsics.checkParameterIsNotNull(direction, "direction");
        return new FileTreeWalk(walk, direction);
    }

    public static final FileTreeWalk walkTopDown(File walkTopDown) {
        Intrinsics.checkParameterIsNotNull(walkTopDown, "$this$walkTopDown");
        return FilesKt.walk(walkTopDown, FileWalkDirection.TOP_DOWN);
    }

    public static final FileTreeWalk walkBottomUp(File walkBottomUp) {
        Intrinsics.checkParameterIsNotNull(walkBottomUp, "$this$walkBottomUp");
        return FilesKt.walk(walkBottomUp, FileWalkDirection.BOTTOM_UP);
    }
}
