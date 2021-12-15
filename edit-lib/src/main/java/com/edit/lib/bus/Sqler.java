package com.edit.lib.bus;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;

public class Sqler {
    private final SQLObject mine;
    private final SQLExpr parent;

    public Sqler(SQLObject mine, SQLExpr parent) {
        this.mine = mine;
        this.parent = parent;
    }

    public boolean matches(String regex) {
        if (this.mine instanceof SQLIdentifierExpr) {
            return ((SQLIdentifierExpr)this.mine).getName().matches(regex);
        } else {
            return this.mine instanceof SQLCharExpr && ((SQLCharExpr) this.mine).getText().matches(regex);
        }
    }

    public void remove() {
        if (this.parent != null) {
            SQLObject object = this.parent.getParent();
            if (object instanceof MySqlSelectQueryBlock) {
                ((MySqlSelectQueryBlock)object).setWhere((SQLExpr)null);
                return;
            }

            SQLBinaryOpExpr parent = (SQLBinaryOpExpr)object;
            SQLExpr left;
            SQLObject pp;
            if (parent.getLeft().equals(this.parent)) {
                left = parent.getRight();
                pp = parent.getParent();
                if (pp instanceof SQLBinaryOpExpr) {
                    ((SQLBinaryOpExpr)pp).setLeft(left);
                }

                if (pp instanceof MySqlSelectQueryBlock) {
                    ((MySqlSelectQueryBlock)pp).setWhere(left);
                }
            }

            if (parent.getRight().equals(this.parent)) {
                left = parent.getLeft();
                pp = parent.getParent();
                if (pp instanceof SQLBinaryOpExpr) {
                    ((SQLBinaryOpExpr)pp).setLeft(left);
                }

                if (pp instanceof MySqlSelectQueryBlock) {
                    ((MySqlSelectQueryBlock)pp).setWhere(left);
                }
            }
        }

    }

    public String toString() {
        return "Sqler{ mine=" + this.mine + ", parent=" + this.parent + '}';
    }

    public void replace(SQLExpr expr) {
        SQLBinaryOpExpr parent = (SQLBinaryOpExpr)this.mine.getParent();
        parent.setRight(expr);
    }
}
