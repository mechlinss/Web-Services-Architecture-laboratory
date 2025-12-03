export interface Category {
  id: string;
  name: string;
  department?: string;
  description?: string;
}

export interface CategoryCreate {
  name: string;
  department?: string;
  description?: string;
}
