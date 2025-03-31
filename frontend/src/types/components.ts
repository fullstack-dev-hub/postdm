export interface InputFieldProps {
  label: string;
  type: string;
  placeholder: string;
  value?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  children?: React.ReactNode;
  helperText?: string;
  disabled?: boolean;
}

export interface PrimaryButtonProps {
  text: string;
  onClick?: () => void;
  disabled?: boolean;
}
