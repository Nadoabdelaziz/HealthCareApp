package com.example.healthcare.models;


public class Code128Constant {
    public static final byte[][] CODE_WEIGHT = {
            {2, 1, 2, 2, 2, 2}, // 0
            {2, 2, 2, 1, 2, 2},
            {2, 2, 2, 2, 2, 1},
            {1, 2, 1, 2, 2, 3},
            {1, 2, 1, 3, 2, 2},
            {1, 3, 1, 2, 2, 2}, // 5
            {1, 2, 2, 2, 1, 3},
            {1, 2, 2, 3, 1, 2},
            {1, 3, 2, 2, 1, 2},
            {2, 2, 1, 2, 1, 3},
            {2, 2, 1, 3, 1, 2}, // 10
            {2, 3, 1, 2, 1, 2},
            {1, 1, 2, 2, 3, 2},
            {1, 2, 2, 1, 3, 2},
            {1, 2, 2, 2, 3, 1},
            {1, 1, 3, 2, 2, 2}, // 15
            {1, 2, 3, 1, 2, 2},
            {1, 2, 3, 2, 2, 1},
            {2, 2, 3, 2, 1, 1},
            {2, 2, 1, 1, 3, 2},
            {2, 2, 1, 2, 3, 1}, // 20
            {2, 1, 3, 2, 1, 2},
            {2, 2, 3, 1, 1, 2},
            {3, 1, 2, 1, 3, 1},
            {3, 1, 1, 2, 2, 2},
            {3, 2, 1, 1, 2, 2}, // 25
            {3, 2, 1, 2, 2, 1},
            {3, 1, 2, 2, 1, 2},
            {3, 2, 2, 1, 1, 2},
            {3, 2, 2, 2, 1, 1},
            {2, 1, 2, 1, 2, 3}, // 30
            {2, 1, 2, 3, 2, 1},
            {2, 3, 2, 1, 2, 1},
            {1, 1, 1, 3, 2, 3},
            {1, 3, 1, 1, 2, 3},
            {1, 3, 1, 3, 2, 1}, // 35
            {1, 1, 2, 3, 1, 3},
            {1, 3, 2, 1, 1, 3},
            {1, 3, 2, 3, 1, 1},
            {2, 1, 1, 3, 1, 3},
            {2, 3, 1, 1, 1, 3}, // 40
            {2, 3, 1, 3, 1, 1},
            {1, 1, 2, 1, 3, 3},
            {1, 1, 2, 3, 3, 1},
            {1, 3, 2, 1, 3, 1},
            {1, 1, 3, 1, 2, 3}, // 45
            {1, 1, 3, 3, 2, 1},
            {1, 3, 3, 1, 2, 1},
            {3, 1, 3, 1, 2, 1},
            {2, 1, 1, 3, 3, 1},
            {2, 3, 1, 1, 3, 1}, // 50
            {2, 1, 3, 1, 1, 3},
            {2, 1, 3, 3, 1, 1},
            {2, 1, 3, 1, 3, 1},
            {3, 1, 1, 1, 2, 3},
            {3, 1, 1, 3, 2, 1}, // 55
            {3, 3, 1, 1, 2, 1},
            {3, 1, 2, 1, 1, 3},
            {3, 1, 2, 3, 1, 1},
            {3, 3, 2, 1, 1, 1},
            {3, 1, 4, 1, 1, 1}, // 60
            {2, 2, 1, 4, 1, 1},
            {4, 3, 1, 1, 1, 1},
            {1, 1, 1, 2, 2, 4},
            {1, 1, 1, 4, 2, 2},
            {1, 2, 1, 1, 2, 4}, // 65
            {1, 2, 1, 4, 2, 1},
            {1, 4, 1, 1, 2, 2},
            {1, 4, 1, 2, 2, 1},
            {1, 1, 2, 2, 1, 4},
            {1, 1, 2, 4, 1, 2}, // 70
            {1, 2, 2, 1, 1, 4},
            {1, 2, 2, 4, 1, 1},
            {1, 4, 2, 1, 1, 2},
            {1, 4, 2, 2, 1, 1},
            {2, 4, 1, 2, 1, 1}, // 75
            {2, 2, 1, 1, 1, 4},
            {4, 1, 3, 1, 1, 1},
            {2, 4, 1, 1, 1, 2},
            {1, 3, 4, 1, 1, 1},
            {1, 1, 1, 2, 4, 2}, // 80
            {1, 2, 1, 1, 4, 2},
            {1, 2, 1, 2, 4, 1},
            {1, 1, 4, 2, 1, 2},
            {1, 2, 4, 1, 1, 2},
            {1, 2, 4, 2, 1, 1}, // 85
            {4, 1, 1, 2, 1, 2},
            {4, 2, 1, 1, 1, 2},
            {4, 2, 1, 2, 1, 1},
            {2, 1, 2, 1, 4, 1},
            {2, 1, 4, 1, 2, 1}, // 90
            {4, 1, 2, 1, 2, 1},
            {1, 1, 1, 1, 4, 3},
            {1, 1, 1, 3, 4, 1},
            {1, 3, 1, 1, 4, 1},
            {1, 1, 4, 1, 1, 3}, // 95
            {1, 1, 4, 3, 1, 1},
            {4, 1, 1, 1, 1, 3},
            {4, 1, 1, 3, 1, 1},
            {1, 1, 3, 1, 4, 1},
            {1, 1, 4, 1, 3, 1}, // 100
            {3, 1, 1, 1, 4, 1},
            {4, 1, 1, 1, 3, 1},
            {2, 1, 1, 4, 1, 2},
            {2, 1, 1, 2, 1, 4},
            {2, 1, 1, 2, 3, 2}, // 105
            {2, 3, 3, 1, 1, 1, 2}
    };
}